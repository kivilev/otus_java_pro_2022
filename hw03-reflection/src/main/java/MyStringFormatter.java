import java.security.InvalidParameterException;

public class MyStringFormatter {
    public static String formatString(String sourceString, String... args) {
        if (sourceString == null) {
            throw new InvalidParameterException("Empty source string");
        }
        if (args.length == 0) {
            throw new InvalidParameterException("Empty format parameters");
        }
        return String.format(sourceString, (Object[]) args);
    }
}
