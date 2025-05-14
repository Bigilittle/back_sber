from typing import List, Dict, Any
from collections import Counter
from itertools import product
from dice_expectation import calculate_distributions, InputCasts

def combine_with_crit(
    base_dist: Dict[int, float],
    crit_dist: Dict[int, float],
    p_total_hit: float,
    p_crit: float = 0.05
) -> Dict[int, float]:
    p_hit_regular = max(p_total_hit - p_crit, 0)
    p_miss = max(0.05, 1.0 - p_hit_regular - p_crit)

    final_dist: Dict[int, float] = {}

    for dmg, prob in base_dist.items():
        final_dist[dmg] = final_dist.get(dmg, 0) + prob * p_hit_regular

    for dmg, prob in crit_dist.items():
        final_dist[dmg] = final_dist.get(dmg, 0) + prob * p_crit

    final_dist[0] = final_dist.get(0, 0) + p_miss

    return final_dist

def d20_success_chance(threshold: int, modifier: int, advantage: bool = False) -> float:
    rolls = [i for i in range(1, 21)]
    if not advantage:
        successes = sum(1 for roll in rolls if roll + modifier >= threshold)
        return round(successes / 20.0, 3)
    else:
        total = 0
        for r1 in rolls:
            for r2 in rolls:
                if max(r1, r2) + modifier >= threshold:
                    total += 1
        return round(total / 400.0, 3)

def d20_fail_save_chance(dc: int, bonus: int) -> float:
    fails = sum(1 for roll in range(1, 21) if roll + bonus < dc)
    return round(fails / 20.0, 3)

def process_attack_data(data: Dict[str, Any]) -> Dict[str, Any]:
    attacks = data["attacks"]
    defense = data["defenseSettings"]
    kb = int(defense["kb"])
    damage_mods = defense["damageMods"]
    saves = defense["saves"]

    results = []
    total_counter = Counter()

    for index, attack in enumerate(attacks):
        if not attack.get("damageParams"):
            continue

        damage_info = attack["damageParams"][0]
        dice = damage_info["damage"]
        dmg_type = damage_info["type"]
        attack_id = attack["id"]
        roll_type = attack["rollType"]
        value = int(attack["value"])
        advantage = attack.get("hasExtra", False)

        mod = damage_mods.get(dmg_type, "Обычное")
        casts = {
            "vulnerability": [],
            "ordinary": [],
            "stability": [],
            "modifier": 0
        }
        if mod == "Уязвимость":
            casts["vulnerability"].append(dice)
        elif mod == "Устойчивость":
            casts["stability"].append(dice)
        else:
            casts["ordinary"].append(dice)

        out = calculate_distributions(InputCasts(**casts))
        base_dist = out.all
        crit_dist = {k * 2: v for k, v in base_dist.items()}

        if roll_type == "attack":
            p_total_hit = d20_success_chance(kb, value, advantage)
        elif roll_type == "save":
            save_type = attack.get("saveType", "")
            bonus = saves.get(save_type, 0)
            dc = value
            fail_prob = d20_fail_save_chance(dc, bonus)
            success_prob = 1 - fail_prob

            if attack.get("hasExtra", False):
                p_total_hit = fail_prob + success_prob * 0.5
            else:
                p_total_hit = fail_prob
        else:
            p_total_hit = 0.0

        p_crit = 0.05

        final = combine_with_crit(base_dist, crit_dist, p_total_hit, p_crit)

        results.append({
            "id": index,
            "damage": {str(k): round(v, 3) for k, v in sorted(final.items())}
        })

        total_counter.update(final)

    total_sum = sum(total_counter.values())
    total_distribution = {
        str(k): round(v / total_sum, 3) for k, v in sorted(total_counter.items())
    }

    results.append({
        "id": "Общее значение",
        "damage": total_distribution
    })

    return results

def process_attack_dumb_data(data: Dict[str, List[str]]) -> List[Dict[str, Any]]:
    dice_list = data.get('dice', [])
    results: List[Dict[str, Any]] = []
    total_counter = Counter()

    for index, dice in enumerate(dice_list):
        casts = InputCasts(
            vulnerability=[],
            ordinary=[dice],
            stability=[],
            modifier=0
        )
        out = calculate_distributions(casts)
        base_dist = out.all

        results.append({
            'id': index,
            'damage': {str(k): round(v, 3) for k, v in sorted(base_dist.items())}
        })
        total_counter.update(base_dist)

    total_sum = sum(total_counter.values())
    total_distribution = {
        str(k): round(v / total_sum, 3)
        for k, v in sorted(total_counter.items())
    }
    results.append({
        'id': 'Общее значение',
        'damage': total_distribution
    })

    return results
