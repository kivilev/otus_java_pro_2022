package ru.otus.appcontainer;

import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exceptions.ComponentNotFoundException;
import ru.otus.exceptions.DuplicateComponentNameException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.TypesAnnotated;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, Object> appComponentsByClass = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
        processConfigs(initialConfigClasses);
    }

    public AppComponentsContainerImpl(String initialPackageScanPath) {
        processConfigs(searchAppComponentsContainerConfigClasses(initialPackageScanPath));
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        C component = (C) appComponentsByClass.get(componentClass);
        if (component == null) {
            var implementedInterfaces = componentClass.getInterfaces();
            for (var implementedInterface : implementedInterfaces) {
                var object = appComponentsByClass.get(implementedInterface);
                if (object != null) {
                    component = (C) object;
                    break;
                }
            }
        }

        if (component == null) {
            throw new ComponentNotFoundException(String.format("Component with class %s not found", componentClass.getSimpleName()));
        }
        return component;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C component = (C) appComponentsByName.get(componentName);
        if (component == null) {
            throw new ComponentNotFoundException(String.format("Component with name %s not found", componentName));
        }
        return component;
    }

    private Class<?>[] searchAppComponentsContainerConfigClasses(String initialPackageScanPath) {
        return new Reflections(initialPackageScanPath, TypesAnnotated)
                .getTypesAnnotatedWith(AppComponentsContainerConfig.class).toArray(new Class[0]);
    }

    private void processConfigs(Class<?>[] initialConfigClasses) {
        for (var configClass : initialConfigClasses) {
            checkConfigClass(configClass);
        }

        for (var configClass : getSortedConfigClassesByAnnotationOrder(initialConfigClasses)) {
            processConfig(configClass);
        }
    }

    private List<Class<?>> getSortedConfigClassesByAnnotationOrder(Class<?>... initialConfigClasses) {
        return Arrays.stream(initialConfigClasses)
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponentsContainerConfig.class).order())).collect(Collectors.toList());
    }

    private void processConfig(Class<?> configClass) {
        try {
            var configObject = configClass.getDeclaredConstructor().newInstance();
            var methodList = getSortedMethodsByAnnotationOrder(configClass);

            for (var method : methodList) {
                var returnedClass = method.getReturnType();
                var componentName = method.getAnnotation(AppComponent.class).name();

                if (appComponentsByName.containsKey(componentName)) {
                    throw new DuplicateComponentNameException(String.format("Component with name %s already exists", componentName));
                }

                Object createdComponent = createComponent(configObject, method);

                appComponentsByName.put(componentName, createdComponent);
                appComponentsByClass.put(returnedClass, createdComponent);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private List<Method> getSortedMethodsByAnnotationOrder(Class<?> configClass) {
        return Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order())).collect(Collectors.toList());
    }

    private Object createComponent(Object configObject, Method method) throws IllegalAccessException, InvocationTargetException {
        List<Object> argList = new ArrayList<>();
        for (var argument : method.getParameterTypes()) {
            if (!appComponentsByClass.containsKey(argument)) {
                throw new ComponentNotFoundException(String.format("Component with class %s not found", argument.getSimpleName()));
            }
            argList.add(appComponentsByClass.get(argument));
        }
        return method.invoke(configObject, argList.toArray());
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }
}
