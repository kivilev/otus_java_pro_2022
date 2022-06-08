package ru.kivilev.lambda;

@FunctionalInterface
public interface RunnableWithParams<T> {
    void applay(T param1);
}
