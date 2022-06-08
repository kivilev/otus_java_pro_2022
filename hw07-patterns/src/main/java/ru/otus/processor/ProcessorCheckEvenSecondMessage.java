package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.processor.exception.EvenSecondException;

import java.time.LocalDateTime;

public class ProcessorCheckEvenSecondMessage implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorCheckEvenSecondMessage(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        LocalDateTime dateTime = dateTimeProvider.getDateTime();
        if (dateTime.getSecond() % 2 == 0) {
            throw new EvenSecondException();
        }
        return message;
    }
}
