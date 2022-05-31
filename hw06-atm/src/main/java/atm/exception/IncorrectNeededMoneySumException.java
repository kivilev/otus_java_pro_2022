package atm.exception;

public class IncorrectNeededMoneySumException extends RuntimeException {
    public IncorrectNeededMoneySumException() {
    }

    public IncorrectNeededMoneySumException(String message) {
        super(message);
    }
}
