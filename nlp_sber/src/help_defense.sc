theme: /

    state: HelpDefenseMenu
        q!: (Расширенная версия: Защита)
        a: Выберите нужный раздел по защите.
        buttons:
            "как установить класс брони" -> /HelpDefense_SetKB
            "как установить спасбросок" -> /HelpDefense_SetSave
            "как поставить модификатор защиты" -> /HelpDefense_SetResistance
            "как перейти в упрощённый режим" -> /HelpDefense_ToSimple
            "Назад" -> /HelpStart

    state: HelpDefense_SetKB
        q!: (как установить класс брони)
        a: Пример: установи класс брони семь
        buttons:
            "Назад" -> /HelpDefenseMenu

    state: HelpDefense_SetSave
        q!: (как установить спасбросок)
        a: Пример: установи спасбросок шесть по ловкости
        buttons:
            "Назад" -> /HelpDefenseMenu

    state: HelpDefense_SetResistance
        q!: (как поставить модификатор защиты)
        a: Пример: поставь модификатор защиты уязвимость к молнии
        buttons:
            "Назад" -> /HelpDefenseMenu

    state: HelpDefense_ToSimple
        q!: (как перейти в упрощённый режим)
        a: Пример: перейди в упрощённый режим
        buttons:
            "Назад" -> /HelpDefenseMenu
