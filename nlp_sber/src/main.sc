require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: defense.sc
require: simplified.sc
require: advanced.sc
require: help_start.sc
require: help_simple.sc
require: help_defense.sc
require: help_advice.sc

patterns:
    $AnyText = $nonEmptyGarbage
    $Index = $nonEmptyGarbage
    $Value = $nonEmptyGarbage
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.

    state: Приветствие
        intent!: /привет
        a: Привет привет

    state: Прощание
        intent!: /пока
        a: Пока пока

    state: Fallback
        event!: noMatch
        a: Я вас не понял, хотите посмотреть список доступных команд?
        buttons:
            "Нужна помощь" -> /HelpStart












