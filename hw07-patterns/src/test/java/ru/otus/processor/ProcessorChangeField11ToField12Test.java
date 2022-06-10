package ru.otus.processor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

class ProcessorChangeField11ToField12Test {

    private final ProcessorChangeField11ToField12 processorChangeField11ToField12 = new ProcessorChangeField11ToField12();

    @Test
    public void changeField11ToField12CorrectlyWorks() {
        final String FIELD_11_VALUE = "field11_value";
        final String FIELD_12_VALUE = "field12_value";
        Message initialMessage = new Message.Builder(1L).field11(FIELD_11_VALUE).field12(FIELD_12_VALUE).build();
        var resultMessage = processorChangeField11ToField12.process(initialMessage);
        Assertions.assertThat(initialMessage.getField12()).isEqualTo(resultMessage.getField11());
        Assertions.assertThat(initialMessage.getField11()).isEqualTo(resultMessage.getField12());
    }


}