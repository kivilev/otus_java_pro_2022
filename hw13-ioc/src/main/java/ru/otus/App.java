package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.config.AppConfig;
import ru.otus.config.AppConfig1;
import ru.otus.config.AppConfig2;
import ru.otus.services.GameProcessor;
import ru.otus.services.GameProcessorImpl;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.
Можно добавлять свои исключения.

Раскоментируйте тест:
@Disabled //надо удалить
Тест и демо должны проходить для всех реализованных вариантов
Не называйте свой проект ДЗ "homework-template", это имя заготовки)

PS Приложение представляет собой тренажер таблицы умножения
*/

public class App {

    public static void main(String[] args) throws Exception {
        // Опциональные варианты - РАБОТАЕТ
        AppComponentsContainer container2 = new AppComponentsContainerImpl(AppConfig1.class, AppConfig2.class);

        // Тут можно использовать библиотеку Reflections (см. зависимости) - РАБОТАЕТ
        // AppComponentsContainer container3 = new AppComponentsContainerImpl("ru.otus.config");

        // Обязательный вариант - РАБОТАЕТ
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

        // Приложение должно работать в каждом из указанных ниже вариантов - РАБОТАЮТ ВСЕ ВАРИАНТЫ
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        GameProcessor gameProcessor2 = container2.getAppComponent(GameProcessorImpl.class);
        GameProcessor gameProcessor3 = container.getAppComponent("gameProcessor");

        gameProcessor.startGame();
        gameProcessor2.startGame();
        gameProcessor3.startGame();
    }
}
