theme: /
    state: ДобавлениеКуба
        q!: (добавь|запиши|добавить|прими|введи|сохрани) (куб|урон|бросок|поле) $AnyText::anyText
        q!: [нужно] (добавить|внести) $AnyText::anyText
        q!: (бросок) $AnyText::anyText [добавь]

        random:
            a: Принято: {{$parseTree._anyText}}.
            a: Добавил: {{$parseTree._anyText}}.
            a: Записал бросок: {{$parseTree._anyText}}.
            a: Бросок {{$parseTree._anyText}} принят.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "add_dice",
                                dice: $parseTree._anyText
                            }
                        }
                    }]
                }
            });

    state: ПереходНаРасширеннуюВерсию
        q!: (перейди|зайди|открой|перейти) [пожалуйста] $AnyText::anyText (расширенную|продвинутую) (версию)
        q!: (включи|активируй) расширенный режим

        random:
            a: Перехожу на расширенную версию…
            a: Открываю расширенную версию.
            a: Готово! Переход на продвинутую версию выполнен.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "go_advanced"
                            }
                        }
                    }]
                }
            });

    state: ДобавитьПоле
        q!: (добавь|добавить|создай|вставь) (поле|запись|значение|куб)
        q!: [нужно] (добавить|создать) $AnyText::value

        random:
            a: Поле добавлено: {{$parseTree._value}}.
            a: Записал: {{$parseTree._value}}.
            a: Добавил запись "{{$parseTree._value}}".
            a: Готово! {{$parseTree._value}} теперь в списке.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "add_field",
                                value: $parseTree._value
                            }
                        }
                    }]
                }
            });

    
    state: ИзменитьПоле
        q!: (измени|обнови|поменяй) $Value::index (поле|значение|атаку|куб) (на) $Value::newValue
        q!: (замени) $Value::index [на] $Value::newValue

        random:
            a: Поле {{$parseTree._index}} обновлено на: {{$parseTree._newValue}}.
            a: Готово! {{$parseTree._index}} → {{$parseTree._newValue}}.
            a: Значение {{$parseTree._index}} заменено.
            a: Обновил поле {{$parseTree._index}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "update_field",
                                index: $parseTree._index,
                                value: $parseTree._newValue
                            }
                        }
                    }]
                }
            });

    state: УдалитьПоле
        q!: (удали|убери|стереть|очисти) [поле|значение|атаку|куб] $Value::index [поле|значение|атаку|куб]
        q!: (удалить) $Value::index

        random:
            a: Поле {{$parseTree._index}} удалено.
            a: Удалил: {{$parseTree._index}}.
            a: Запись {{$parseTree._index}} стерта.
            a: Готово, {{$parseTree._index}} больше нет.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "delete_field",
                                index: $parseTree._index
                            }
                        }
                    }]
                }
            });

    state: ОчиститьВсе
        q!: (очисти|очисть|сбрось|удали все|стереть все|обнули|обнови) [поля|все|записи]
        q!: (сброс|полный сброс)

        random:
            a: Все поля очищены.
            a: Всё удалено, начинаем с чистого листа.
            a: Готово, список очищен.
            a: Очистка выполнена.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "clear_all"
                            }
                        }
                    }]
                }
            });




