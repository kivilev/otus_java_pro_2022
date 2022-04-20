import annotations.After;
import annotations.Before;
import annotations.Test;

public class SetupFailedTest {

    @Before
    public void setup() {
        System.out.println("it's setup method");
        throw new RuntimeException("some exception happens");
    }

    @After
    public void cleanup() {
        System.out.println("it's cleanup method");
    }

    @Test(displayName = "Обычный пустой тест номер 1, который не выполнится")
    public void simpleTest1() {
        System.out.println("this is simple test 1");
    }

    @Test(displayName = "Обычный пустой тест номер 2, который не выполнится")
    public void simpleTest2() {
        System.out.println("this is simple test 2");
    }

}
