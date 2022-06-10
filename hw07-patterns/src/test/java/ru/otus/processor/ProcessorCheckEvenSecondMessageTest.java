package ru.otus.processor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.exception.EvenSecondException;

import java.time.LocalDateTime;

class ProcessorCheckEvenSecondMessageTest {

    Message message = new Message.Builder(1L).build();

    @Test
    public void evenSecondShouldThrowException() {
        try {
            DateTimeProvider dateTimeProvider = new DateTimeProvider(
                    LocalDateTime.of(2022, 02, 24, 4, 01, 00));
            ProcessorCheckEvenSecondMessage evenSecondMessage = new ProcessorCheckEvenSecondMessage(dateTimeProvider);
            evenSecondMessage.process(message);
            Assertions.failBecauseExceptionWasNotThrown(EvenSecondException.class);
        } catch (EvenSecondException e) {
            Assertions.assertThat(e).hasNoCause();
        }
    }

    @Test
    public void oddSecondIsValid() {
        DateTimeProvider dateTimeProvider = new DateTimeProvider(
                LocalDateTime.of(2022, 02, 24, 4, 01, 01));
        ProcessorCheckEvenSecondMessage evenSecondMessage = new ProcessorCheckEvenSecondMessage(dateTimeProvider);
        evenSecondMessage.process(message);
    }

}