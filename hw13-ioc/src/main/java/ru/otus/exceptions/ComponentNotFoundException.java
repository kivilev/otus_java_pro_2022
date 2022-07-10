package ru.otus.exceptions;

public class ComponentNotFoundException extends RuntimeException {

    public ComponentNotFoundException() {
    }

    public ComponentNotFoundException(String message) {
        super(message);
    }
}
