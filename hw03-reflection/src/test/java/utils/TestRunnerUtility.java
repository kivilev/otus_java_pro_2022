package utils;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static utils.ReflectionHelper.getMethodStreamByAnnotation;

public class TestRunnerUtility {

    public static void runClassWithTests(Class<?> testClass) {
        System.out.printf("=== Test class %s ===%n", testClass.getSimpleName());
        if (Arrays.stream(testClass.getMethods()).noneMatch(it -> it.isAnnotationPresent(Test.class))) {
            System.out.println("тесты не найдены");
            return;
        }

        Optional<Method> methodBefore = getMethodStreamByAnnotation(testClass, Before.class).findFirst();
        Optional<Method> methodAfter = getMethodStreamByAnnotation(testClass, After.class).findFirst();
        List<Method> testMethods = getMethodStreamByAnnotation(testClass, Test.class)
                .sorted(Comparator.comparing(Method::getName))
                .toList();

        int succesfullTestsCount = 0;
        int failedTestsCount = 0;

        for (Method method : testMethods) {
            System.out.printf(">> %s...\n", method.getAnnotation(Test.class).displayName());

            Object object = ReflectionHelper.instantiate(testClass);

            try {
                methodBefore.ifPresent(value -> ReflectionHelper.callMethod(object, value.getName()));
                ReflectionHelper.callMethod(object, method.getName());
                System.out.println("\t\tтест успешно пройден");
                succesfullTestsCount++;
            } catch (Exception e) {
                System.out.printf("\t\tпроизошло исключение: %s%n", e);
                failedTestsCount++;
            } finally {
                methodAfter.ifPresent(value -> callAfterMethod(value, object));
            }
        }

        System.out.printf("\t>> Всего тестов: %s. Успешных: %s. С ошибками: %s\n",
                (succesfullTestsCount + failedTestsCount),
                succesfullTestsCount,
                failedTestsCount);
    }

    public static void runClassesWithTests(List<Class<?>> testClasses) {
        for (Class<?> testClass : testClasses) {
            runClassWithTests(testClass);
        }
    }

    private static void callAfterMethod(Method methodAfter, Object object) {
        try {
            ReflectionHelper.callMethod(object, methodAfter.getName());
        } catch (Exception e) {
            System.out.printf("\t\tпроизошло исключение (after): %s%n", e);
        }
    }
}
