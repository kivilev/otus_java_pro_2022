package ru.kivilev;

import ru.kivilev.annotations.Log;

public class TestCalculation implements Calculation {

    @Log
    public void calculation(int param) {
        System.out.printf("Calculation with one parameter. Sum of args: %s\n", param);
    }

    @Log
    public void calculation(int param, int param2) {
        System.out.printf("Calculation with one parameter. Sum of args: %s\n", param + param2);
    }

    public void calculation(int param, int param2, int param3) {
        System.out.printf("Calculation with three parameter. Sum of args: %s\n", param + param2 + param3);
    }
}
