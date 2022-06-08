package ru.otus.processor;

import java.time.LocalDateTime;

public class DateTimeProvider {

    private final LocalDateTime dateTime;

    public DateTimeProvider(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }
}
