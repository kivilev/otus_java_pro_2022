package ru.kivilev;

import ru.kivilev.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ioc {
    public static TestLogging createTestLoggingClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;
        private final List<Method> logMethods;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
            this.logMethods = Arrays.stream(myClass.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .collect(Collectors.toList());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isMethodWithLogging(method, args)) {
                System.out.printf("\tLOG -> Method: %s, Params: %s\n", method.getName(), Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }

        private boolean isMethodWithLogging(Method method, Object[] args) {
            for (var logMethod : this.logMethods) {
                if (logMethod.getName().equals(method.getName()) && args.length == logMethod.getParameterCount()) {
                    return true;
                }
            }
            return false;
        }
    }
}
