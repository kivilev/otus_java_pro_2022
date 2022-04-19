import utils.TestRunnerUtility;

import java.util.Arrays;

public class TestApp {
    public static void main(String[] args) {
        TestRunnerUtility.runClassesWithTests(Arrays.asList(
                SetupFailedTest.class,
                MyStringFormatterTest.class,
                EmptyTest.class
        ));
    }
}
