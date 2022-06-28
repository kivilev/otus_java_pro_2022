package ru.kivilev;

/*
* java -javaagent:proxyLog.jar -jar proxyLog.jar
* */
public class MainApp {
    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createTestLoggingClass();
        testLogging.calculation(1);
        testLogging.calculation(1, 2);
        testLogging.calculation(1, 2, "str3");
        testLogging.calculation(1, 2, "str3", "str4");
    }
}
