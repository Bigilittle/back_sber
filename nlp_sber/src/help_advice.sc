theme: /

    state: HelpAdviceMenu
        q!: (Расширенная версия: Атаки)
        a: Выберите, с каким действием нужна помощь.
        buttons:
            "как добавить новую атаку" -> /HelpAdvice_AddAttack
            "как удалить атаку" -> /HelpAdvice_DeleteAttack
            "как установить тип атаки: бросок" -> /HelpAdvice_SetRollAttack
            "как установить тип атаки: спасбросок" -> /HelpAdvice_SetRollSave
            "как задать модификатор атаки" -> /HelpAdvice_SetModifier
            "как задать сложность атаки" -> /HelpAdvice_SetDifficulty
            "как задать характеристику" -> /HelpAdvice_SetStat
            "как включить/выключить флаг атаки" -> /HelpAdvice_ToggleFlag
            "как добавить параметр урона" -> /HelpAdvice_AddDamage
            "как удалить параметр урона" -> /HelpAdvice_DeleteDamage
            "как записать формулу урона" -> /HelpAdvice_SetDamageFormula
            "как установить тип урона" -> /HelpAdvice_SetDamageType
            "как отправить данные" -> /HelpAdvice_SendAttack
            "как перейти в упрощённый режим" -> /HelpDefense_ToSimple
            "Назад" -> /HelpStart

    state: HelpAdvice_AddAttack
        q!: (как добавить новую атаку)
        a: Пример: добавь новую атаку
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_DeleteAttack
        q!: (как удалить атаку)
        a: Пример: удали атаку один
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_SetRollAttack
        q!: (как установить тип атаки: бросок)
        a: Пример: установи тип атаки бросок атаки для первой атаки
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_SetRollSave
        q!: (как установить тип атаки: спасбросок)
        a: Пример: установи тип атаки спас для второй атаки
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_SetStat
        q!: (как задать характеристику)
        a: Пример: задай характеристику атаки сила для второй атаки
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_SetModifier
        q!: (как задать модификатор атаки)
        a: Пример: введи модификатор девять для первой атаки
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_SetDifficulty
        q!: (как задать сложность атаки)
        a: Пример: введи сложность минус шесть для второй атаки
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_ToggleFlag
        q!: (как включить/выключить флаг атаки)
        a: 
            Примеры:
            включи режим преимущества для первой атаки  
            включи флаг половины урона для второй атаки  
            убери режим преимущества для первой атаки  
            убери флаг половины урона для второй атаки
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_AddDamage
        q!: (как добавить параметр урона)
        a: Пример: добавь параметр урона для первой атаки
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_DeleteDamage
        q!: (как удалить параметр урона)
        a: Пример: удали параметр один для первой атаки
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_SetDamageFormula
        q!: (как записать формулу урона)
        a: Пример: запиши формулу один д десять плюс три для атаки один для параметра два
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_SetDamageType
        q!: (как установить тип урона)
        a: Пример: установи тип урона рубящий для атаки один для параметра два
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpAdvice_SendAttack
        q!: (как отправить данные)
        a: Пример: отправь все данные
        buttons:
            "Назад" -> /HelpAdviceMenu

    state: HelpDefense_ToSimple
        q!: (как перейти в упрощённый режим)
        a: Пример: перейди в упрощённый режим
        buttons:
            "Назад" -> /HelpAdviceMenu
