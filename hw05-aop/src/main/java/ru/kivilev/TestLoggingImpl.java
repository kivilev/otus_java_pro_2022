package ru.kivilev;

import ru.kivilev.annotation.Log;

public class TestLoggingImpl implements TestLogging {

    @Log
    public void calculation(int param1) {
        System.out.println("Calculation (LOG). one param.");
    }

    public void calculation(int param1, int param2) {
        System.out.println("Calculation. two param.");
    }

    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Calculation (LOG). three param.");
    }

    public void calculation(int param1, int param2, String param3, String param4) {
        System.out.println("Calculation. four param.");
    }
}
