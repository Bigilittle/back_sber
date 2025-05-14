from collections import Counter
from itertools import product
from typing import List, Dict, Tuple
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


def dice_to_values(dice_string: str) -> List[int]:
    """
    Разворачивает строку кубика (например, '1d6+1') в список всех возможных значений.
    """
    dice_string = str(dice_string)

    if 'd' not in dice_string:
        return [int(dice_string)]

    parts = dice_string.split('d')
    dice_count = int(parts[0])
    remaining = parts[1]

    additional = 0
    if '+' in remaining:
        dice_shapes, add = remaining.split('+')
        additional = int(add)
    elif '-' in remaining:
        dice_shapes, sub = remaining.split('-')
        additional = -int(sub)
    else:
        dice_shapes = remaining

    dice_shapes = int(dice_shapes)
    single_die = [i + additional for i in range(1, dice_shapes + 1)]

    return dice_multiply(single_die, dice_count)


def dice_multiply(values: List[int], count: int) -> List[int]:
    """
    Генерирует все возможные суммы при многократном броске кубика.
    """
    if count == 1:
        return values

    result = []
    for x in values:
        for y in dice_multiply(values, count - 1):
            result.append(x + y)

    return result


def calculate_distribution(values: List[int]) -> Dict[int, float]:
    """
    Строит вероятностное распределение значений.
    """
    total = len(values)
    return {
        v: round(count / total, 2)
        for v, count in Counter(values).items()
    }


def process_component(component_list: List[str]) -> Tuple[Dict[str, Dict[int, float]], List[int]]:
    """
    Обрабатывает список выражений кубиков: возвращает их распределения
    и все возможные суммы значений.
    """
    distributions: Dict[str, Dict[int, float]] = {}
    combined: List[List[int]] = []

    for dice in component_list:
        values = dice_to_values(dice)
        distributions[dice] = calculate_distribution(values)
        combined.append(values)

    if not combined:
        return distributions, []

    result = combined[0]
    for values in combined[1:]:
        result = [x + y for x in result for y in values]

    return distributions, result


def calculate_distributions(input_dict: InputCasts) -> OutputCasts:
    """
    Принимает данные InputCasts, рассчитывает распределения и итоговую модель OutputCasts.
    """
    vuln_dist, vuln_values = process_component(input_dict.vulnerability)
    ord_dist, ord_values = process_component(input_dict.ordinary)
    stab_dist, stab_values = process_component(input_dict.stability)

    vuln_values = [int(x * 2) for x in vuln_values] or [0]
    stab_values = [x // 2 for x in stab_values] or [0]
    ord_values = ord_values or [0]

    result = OutputCasts(
        vulnerability=vuln_dist,
        ordinary=ord_dist,
        stability=stab_dist,
        all={}
    )

    modifier = input_dict.modifier
    all_combinations = product(vuln_values, ord_values, stab_values)
    total = [sum(combo) + modifier for combo in all_combinations]

    counts = Counter(total)
    total_count = len(total)
    result.all = {
        k: round(v / total_count, 2)
        for k, v in counts.items()
    }

    return result
