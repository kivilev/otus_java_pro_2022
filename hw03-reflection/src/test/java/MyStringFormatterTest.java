import annotations.After;
import annotations.Before;
import annotations.Test;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThat;

public class MyStringFormatterTest {

    @Before
    public void setup() {
        System.out.println("it's setup method");
    }

    @After
    public void cleanup() {
        System.out.println("it's cleanup method");
    }

    @Test(displayName = "Форматирование корректной строки с валидными параметрами форматирования")
    public void correctStringWithValidFormatArguments() {
        var expectedString = "My name is Denis";
        var actualString = MyStringFormatter.formatString("My name is %s", "Denis");
        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test(displayName = "Форматирование корректной строки с отсутствующими параметрами форматирования")
    public void correctStringWithoutFormatArguments() {
        try {
            MyStringFormatter.formatString("My name is %s");
        } catch (InvalidParameterException ex) {
            assertThat(ex).hasMessage("Empty format parameters");
        }
    }
}
