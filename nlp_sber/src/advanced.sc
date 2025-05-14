theme: /

    state: СоздатьАтаку
        q!: (создай|добавь|вставь|начни) [новую] атаку
        q!: [мне нужна] новая атака
        q!: (добавить) атаку в список

        a: Готово, создаю атаку.


        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_create_attack"
                            }
                        }
                    }]
                }
            });

    state: УдалитьАтаку
        q!: (удали|убери|стереть) атаку $AnyText::id
        q!: (убери) бросок $AnyText::id
        q!: [удалить] атаку с именем $AnyText::id

        a: Атака {{$parseTree._id}} удалена.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_delete_attack",
                                id: $parseTree._id
                            }
                        }
                    }]
                }
            });

    state: УстановитьТипБроска
        q!: (установи|выбери|запиши) тип (атаки|броска) $AnyText::value (для|у) [атаки|атака] $AnyText::id [атаки|атака]
        q!: (тип броска|тип атаки) $AnyText::value (назначь|введи) для $AnyText::id

        a: Тип броска {{$parseTree._value}} установлен для {{$parseTree._id}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_set_roll_type",
                                id: $parseTree._id,
                                value: $parseTree._value
                            }
                        }
                    }]
                }
            });

    state: УстановитьЗначениеБроска
        q!: (введи|установи|запиши) (модификатор|сложность|значение урона) $AnyText::value (для|к) [атаки|атака] $AnyText::id [атаки|атака]
        q!: [укажи] параметр $AnyText::value (для|к) [атаки|атака] $AnyText::id 

        a: Значение {{$parseTree._value}} установлено для {{$parseTree._id}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_set_value",
                                id: $parseTree._id,
                                value: $parseTree._value
                            }
                        }
                    }]
                }
            });

    state: УстановитьХарактеристику
        q!: (задай|установи|введи) (характеристику атаки|спасбросок) $AnyText::value (для|к) [атака|атаки] $AnyText::id
        q!: [установить] (характеристику|характеристика) $AnyText::value (у|для) [атака|атаки] $AnyText::id [атака|атаки]

        a: Характеристика {{$parseTree._value}} задана для {{$parseTree._id}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_set_save_type",
                                id: $parseTree._id,
                                value: $parseTree._value
                            }
                        }
                    }]
                }
            });

    state: ВключитьФлаг
        q!: (включи|поставь|установи) (режим|флаг|галочку|flag) $AnyText::value (у|для) [атака|атаки] $AnyText::id [атака|атаки]
        q!: [нужно] (включить|включи) [флаг] $AnyText::value (для) [атака|атаки] $AnyText::id [атака|атаки]
        a: Флаг {{$parseTree._value}} включён для {{$parseTree._id}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_toggle_extra",
                                id: $parseTree._id,
                                value: "true"
                            }
                        }
                    }]
                }
            });


    state: ВыключитьФлаг
        q!: (выключи|сними|убери|отключи) (режим|флаг|галочку|flag) $AnyText::value (у|для) [атака|атаки] $AnyText::id [атака|атаки]
        q!: [нужно] (убрать|убери) [флаг] $AnyText::value (для) [атака|атаки] $AnyText::id [атака|атаки]
        a: Флаг {{$parseTree._value}} отключён для {{$parseTree._id}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_toggle_extra",
                                id: $parseTree._id,
                                value: "false"
                            }
                        }
                    }]
                }
            });

    state: ДобавитьУрон
        q!: (добавь|вставь|создай) (параметр урона|тип урона|новый урон) [для] [атака|атаки] $AnyText::id [атака|атаки]
        q!: [хочу] (добавить урон) [к] [атака|атаки] $AnyText::id [атака|атаки]

        a: Параметр урона добавлен для {{$parseTree._id}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_add_damage",
                                id: $parseTree._id
                            }
                        }
                    }]
                }
            });

    state: УдалитьУрон
        q!: (удали|убери) (урон|параметр) [урона] $AnyText::index для [атака|атаки] $AnyText::id [атака|атаки]
        q!: [нужно] (удалить урон) [номер|индекс|под номером] $AnyText::index у [атака|атаки] $AnyText::id [атака|атаки]

        a: Урон {{$parseTree._index}} удалён для {{$parseTree._id}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_delete_damage",
                                id: $parseTree._id,
                                index: $parseTree._index
                            }
                        }
                    }]
                }
            });

    state: УстановитьЗначениеУрона
        q!: (установи|введи|запиши) (формулу) $AnyText::value (для) [атака|атаки] $AnyText::id [атака|атаки] (номер параметра|параметра|для параметра) [под номером] $AnyText::index
        q!: (значение урона) $AnyText::value для $AnyText::id (параметр) $AnyText::index

        a: Значение урона {{$parseTree._value}} задано для {{$parseTree._id}}, параметр {{$parseTree._index}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_set_damage_value",
                                id: $parseTree._id,
                                index: $parseTree._index,
                                value: $parseTree._value
                            }
                        }
                    }]
                }
            });

    state: УстановитьТипУрона
        q!: (установи|введи|запиши) (тип урона) $AnyText::value (для) [атака|атаки] $AnyText::id [атака|атаки] (номер параметра|параметра|для параметра) [под номером] $AnyText::index
        q!: (укажи) тип урона $AnyText::value у $AnyText::id (параметр) $AnyText::index

        a: Тип урона {{$parseTree._value}} установлен для {{$parseTree._id}}, параметр {{$parseTree._index}}.

        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_set_damage_type",
                                id: $parseTree._id,
                                index: $parseTree._index,
                                value: $parseTree._value
                            }
                        }
                    }]
                }
            });

    state: ОтправитьРезультат
        q!: (отправь|посчитай|рассчитай|выполни) (урон|атаку|результат|все данные|данные|кубы)
        q!: (итог|отчёт|завершить) атаку
        q!: (рассчитай урон|расcчитать урон)

        a: Отправляю данные…
        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_send"
                            }
                        }
                    }]
                }
            });

    state: ВернутьсяКПростойВерсии
        q!: (вернись|назад|к простой версии|откатись к простой)
        q!: (перейди) в упрощённый режим

        a: Возвращаюсь к простой версии
        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "raw",
                body: {
                    items: [{
                        command: {
                            type: "smart_app_data",
                            action: {
                                type: "adv_back_simple"
                            }
                        }
                    }]
                }
            });