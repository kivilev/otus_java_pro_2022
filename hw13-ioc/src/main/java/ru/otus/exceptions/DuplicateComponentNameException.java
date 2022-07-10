package ru.otus.exceptions;

public class DuplicateComponentNameException extends RuntimeException {
    public DuplicateComponentNameException() {
    }

    public DuplicateComponentNameException(String message) {
        super(message);
    }
}
