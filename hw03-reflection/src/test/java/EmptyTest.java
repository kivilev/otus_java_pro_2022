import annotations.After;
import annotations.Before;

public class EmptyTest {

    @Before
    public void setup() {
        System.out.println("it's setup method");
    }

    @After
    public void cleanup() {
        System.out.println("it's cleanup method");
    }

}
