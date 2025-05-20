from collections import defaultdict
from typing import List, Dict
from pydantic import BaseModel

class InputCasts(BaseModel):
    vulnerability: List[str]
    ordinary: List[str]
    stability: List[str]
    modifier: int

class OutputCasts(BaseModel):
    vulnerability: Dict[str, Dict[int, float]]
    ordinary: Dict[str, Dict[int, float]]
    stability: Dict[str, Dict[int, float]]
    all: Dict[int, float]

def convolve_dist(d1: Dict[int, float], d2: Dict[int, float]) -> Dict[int, float]:
    result: Dict[int, float] = defaultdict(float)
    for v1, p1 in d1.items():
        for v2, p2 in d2.items():
            result[v1 + v2] += p1 * p2
    return dict(result)

def dice_distribution_map(dice_string: str) -> Dict[int, float]:
    dice_string = str(dice_string)
    if 'd' not in dice_string:
        val = int(dice_string)
        return {val: 1.0}
    count_str, rest = dice_string.split('d')
    dice_count = int(count_str)
    additional = 0
    if '+' in rest:
        sides_str, add_str = rest.split('+')
        additional = int(add_str)
    elif '-' in rest:
        sides_str, sub_str = rest.split('-')
        additional = -int(sub_str)
    else:
        sides_str = rest
    dice_sides = int(sides_str)
    base: Dict[int, float] = {i: 1.0 / dice_sides for i in range(1, dice_sides + 1)}
    dist = base
    for _ in range(dice_count - 1):
        dist = convolve_dist(dist, base)
    if additional:
        dist = {value + additional: prob for value, prob in dist.items()}
    return {value: round(prob, 2) for value, prob in dist.items()}

def calculate_distributions(input_dict: InputCasts) -> OutputCasts:
    vuln_dist_map: Dict[str, Dict[int, float]] = {dice: dice_distribution_map(dice) for dice in input_dict.vulnerability}
    ord_dist_map: Dict[str, Dict[int, float]] = {dice: dice_distribution_map(dice) for dice in input_dict.ordinary}
    stab_dist_map: Dict[str, Dict[int, float]] = {dice: dice_distribution_map(dice) for dice in input_dict.stability}
    def combine_list(dist_maps: List[Dict[int, float]]) -> Dict[int, float]:
        if not dist_maps:
            return {0: 1.0}
        combined = dist_maps[0]
        for dist in dist_maps[1:]:
            combined = convolve_dist(combined, dist)
        return combined
    combined_vuln = combine_list(list(vuln_dist_map.values()))
    combined_ord = combine_list(list(ord_dist_map.values()))
    combined_stab = combine_list(list(stab_dist_map.values()))
    scaled_vuln: Dict[int, float] = {value * 2: prob for value, prob in combined_vuln.items()}
    scaled_stab: Dict[int, float] = {}
    for value, prob in combined_stab.items():
        key = value // 2
        scaled_stab[key] = scaled_stab.get(key, 0.0) + prob
    total_dist = convolve_dist(convolve_dist(scaled_vuln, combined_ord), scaled_stab)
    modifier = input_dict.modifier
    total_dist = {value + modifier: round(prob, 2) for value, prob in total_dist.items()}
    return OutputCasts(vulnerability=vuln_dist_map, ordinary=ord_dist_map, stability=stab_dist_map, all=total_dist)