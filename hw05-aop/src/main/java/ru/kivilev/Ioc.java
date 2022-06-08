package ru.kivilev;

import lombok.SneakyThrows;
import ru.kivilev.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Ioc {

    @SneakyThrows
    public static Calculation getCalculationObject(Class<? extends Calculation> clazz) {
        if (ReflectionUtils.isAnnotationExists(clazz, Log.class)) {
            InvocationHandler handler = new CalculationInvocationHandler(clazz.getDeclaredConstructor().newInstance());
            return (Calculation) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                    new Class<?>[]{Calculation.class}, handler);
        }
        return clazz.getDeclaredConstructor().newInstance();
    }

    static class CalculationInvocationHandler implements InvocationHandler {
        private final Calculation myClass;

        CalculationInvocationHandler(Calculation myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (method.isAnnotationPresent(Log.class)) {
                System.out.println("invoking method:" + method);
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
