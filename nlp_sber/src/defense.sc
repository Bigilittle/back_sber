    
    
theme: /
    state: УстановитьКБ
        q!: [давай] (установи|введи|запиши|поставь|задай) [мне] [пожалуйста] (класс брони|кб) $Value::value
        q!: [нужно] (кб|класс брони) $Value::value
        q!: (укажи|запиши) (кб|брони) $Value::value

        random:
            a: Готово! Класс брони установлен на {{$parseTree._value}}.
            a: Есть! Новый КБ — {{$parseTree._value}}.
            a: Класс брони теперь равен {{$parseTree._value}}.
            a: Записал. Текущее значение КБ: {{$parseTree._value}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_set_kb",
                                value: $parseTree._value
                            }
                        }
                    }]
                }
            });


    state: УстановитьСпасбросок
        q!: (установи|введи|запиши|задай) (спас|спас бросок|спасбросок|сейв|значение спасброска) $Value::value (для|по|на) $Value::stat
        q!: [поставь] сейв $Value::value (на|по) $Value::stat
        q!: [мне нужен] спасбросок $Value::stat $Value::value

        random:
            a: Принято. Спасбросок {{$parseTree._stat}} установлен на {{$parseTree._value}}.
            a: Готово! {{$parseTree._stat}} теперь равен {{$parseTree._value}}.
            a: Записал сейв по {{$parseTree._stat}}: {{$parseTree._value}}.
            a: Сейв на {{$parseTree._stat}} обновлён до {{$parseTree._value}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_set_save",
                                stat: $parseTree._stat,
                                value: $parseTree._value
                            }
                        }
                    }]
                }
            });

    state: УстановитьМодификаторУрона
        q!: (установи|введи|запиши|поставь|задай) (тип защиты|модификатор защиты) $Value::mode (для|по|к) $Value::damageType
        q!: [нужно] (сопротивление|модификатор) $Value::mode (на|по|для) $Value::damageType
        q!: (сделай) $Value::damageType $Value::mode

        random: 
            a: Записал. {{$parseTree._damageType}} теперь установлен как "{{$parseTree._mode}}".
            a: Принято. {{$parseTree._mode}} по {{$parseTree._damageType}} задан.
            a: Тип защиты от {{$parseTree._damageType}} установлен: {{$parseTree._mode}}.
            a: Обновил. {{$parseTree._damageType}} → {{$parseTree._mode}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_set_resistance",
                                damageType: $parseTree._damageType,
                                mode: $parseTree._mode
                            }
                        }
                    }]
                }
            });

