theme: /

    state: HelpSimpleMenu
        q!: (Упрощённая версия)
        a: Выберите, с чем нужна помощь.
        buttons:
            "как добавить куб" -> /HelpSimple_AddDice
            "как добавить поле" -> /HelpSimple_AddField
            "как изменить поле" -> /HelpSimple_UpdateField
            "как удалит поле" -> /HelpSimple_DeleteField
            "как очистить все" -> /HelpSimple_ClearAll
            "как перейти на расширенную версию" -> /HelpSimple_ToAdvanced
            "как получить итог бросков" -> /HelpSimple_Calculate 
            "Назад" -> /HelpStart
    
    state: HelpSimple_AddDice
        q!: (как добавить куб)
        a: Пример: добавь куб один д шесть плюс три
        buttons:
            "Назад" -> /HelpSimpleMenu
    
    state: HelpSimple_AddField
        q!: (как добавить поле)
        a: Пример: добавь поле
        buttons:
            "Назад" -> /HelpSimpleMenu
    
    state: HelpSimple_UpdateField
        q!: (как изменить поле)
        a: Пример: измени первое поле на один д три
        buttons:
            "Назад" -> /HelpSimpleMenu
    
    state: HelpSimple_DeleteField
        q!: (как удалит поле)
        a: Пример: удали второе поле
        buttons:
            "Назад" -> /HelpSimpleMenu
    
    state: HelpSimple_ClearAll
        q!: (как очистить все)
        a: Пример: очисти все
        buttons:
            "Назад" -> /HelpSimpleMenu

    state: HelpSimple_Calculate
        q!: (как получить итог бросков)
        a: Пример: рассчитай урон
        buttons:
            "Назад" -> /HelpSimpleMenu
    
    state: HelpSimple_ToAdvanced
        q!: (как перейти на расширенную версию)
        a: Пример: перейди на расширенную версию
        buttons:
            "Назад" -> /HelpSimpleMenu
    