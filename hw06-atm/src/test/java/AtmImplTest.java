import atm.exception.IncorrectNeededMoneySumException;
import atm.exception.NotEnoughMoneyException;
import atm.model.Atm;
import atm.model.AtmImpl;
import atm.model.BanknoteType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AtmImplTest {

    @Test
    @DisplayName("Хранилище должно возвращать корректный баланс")
    void getBalanceShouldReturnCorrectBalance() {
        long expectedBalance = 100 * 10 + 1000 * 3;

        Atm atm = new AtmImpl();
        atm.putBanknotes(Map.of(BanknoteType.RUB100, 10, BanknoteType.RUB1000, 3));
        var actualBalance = atm.getBalance();

        assertThat(actualBalance).isEqualTo(expectedBalance);
    }

    private static Stream<Arguments> popBanknotesPositiveTestCases() {
        return Stream.of(
                Arguments.of("тест 1",
                        Map.of(BanknoteType.RUB100, 10, BanknoteType.RUB1000, 3),
                        1000L,
                        Map.of(BanknoteType.RUB1000, 1),
                        100 * 10 + 1000 * 3 - 1000
                ),
                Arguments.of("тест 2",
                        Map.of(BanknoteType.RUB100, 1, BanknoteType.RUB1000, 3),
                        100L,
                        Map.of(BanknoteType.RUB100, 1),
                        100 * 1 + 1000 * 3 - 100
                ),
                Arguments.of("тест 3", Map.of(BanknoteType.RUB100, 2),
                        200L,
                        Map.of(BanknoteType.RUB100, 2),
                        100 * 2 - 200
                ),
                Arguments.of("тест 4", Map.of(BanknoteType.RUB500, 2),
                        1000L,
                        Map.of(BanknoteType.RUB500, 2),
                        500 * 2 - 1000
                ),
                Arguments.of("тест 5", Map.of(BanknoteType.RUB500, 2, BanknoteType.RUB100, 2),
                        1200L,
                        Map.of(BanknoteType.RUB500, 2, BanknoteType.RUB100, 2),
                        500 * 2 + 100 * 2 - 1200
                )
        );
    }

    @DisplayName("Получение банкнот должно происходить корректно")
    @ParameterizedTest
    @MethodSource("popBanknotesPositiveTestCases")
    void popBanknotesShouldReturnCorrectBanknotes(String testName,
                                                  Map<BanknoteType, Integer> banknotesInBalance,
                                                  Long neededSum,
                                                  Map<BanknoteType, Integer> expectedBanknotes,
                                                  Integer expectedAtmBalance) {
        Atm atm = new AtmImpl();
        atm.putBanknotes(banknotesInBalance);

        var actualBanknotes = atm.popBanknotes(neededSum);
        Assertions.assertThat(actualBanknotes).isEqualTo(expectedBanknotes);

        var actualAtmBalance = atm.getBalance();
        Assertions.assertThat(actualAtmBalance).isEqualTo(Long.valueOf(expectedAtmBalance));
    }

    @Test
    @DisplayName("Передача негативной суммы к выдаче приводит к ошибке")
    void popNegativeSumShouldLeadToError() {
        try {
            Atm atm = new AtmImpl();
            atm.popBanknotes(-1);
            Assertions.failBecauseExceptionWasNotThrown(InvalidParameterException.class);
        } catch (InvalidParameterException ex) {
            Assertions.assertThat(ex).hasNoCause();
        }
    }

    @Test
    @DisplayName("Передача суммы большей чем есть в хранилище приводит к ошибке")
    void popSumBiggerThenExistsShouldLeadToError() {
        try {
            Atm atm = new AtmImpl();
            atm.putBanknotes(Map.of(BanknoteType.RUB100, 1));
            atm.popBanknotes(200);
            Assertions.failBecauseExceptionWasNotThrown(NotEnoughMoneyException.class);
        } catch (NotEnoughMoneyException ex) {
            Assertions.assertThat(ex).hasNoCause();
        }
    }

    @Test
    @DisplayName("Передача суммы выдачи не бьющейся по имеющимся банкнотам приводит к ошибке")
    void popSumNotSuitableForExistBanknotesShouldLeadToError() {
        try {
            Atm atm = new AtmImpl();
            atm.putBanknotes(Map.of(BanknoteType.RUB500, 1));
            atm.popBanknotes(499);
            Assertions.failBecauseExceptionWasNotThrown(IncorrectNeededMoneySumException.class);
        } catch (IncorrectNeededMoneySumException ex) {
            Assertions.assertThat(ex).hasMessage("Нет подходящих банкнот для выдачи суммы");
        }
    }
}