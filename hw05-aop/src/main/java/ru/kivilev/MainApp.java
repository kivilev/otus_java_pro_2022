package ru.kivilev;

import ru.kivilev.lambda.RunnableWithParams;

public class MainApp {
    public static void main(String[] args) {
        //TestCalculation testCalculation = new TestCalculation();

        /*Calculation testCalculation = Ioc.getCalculationObject(TestCalculation.class);
        testCalculation.calculation(1);
        testCalculation.calculation(2, 3);
        testCalculation.calculation(4, 5, 6);*/

        RunnableWithParams<String> run2 = System.out::println;
        run2.applay("My string");
    }
}