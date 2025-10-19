package tests;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("junit-jupiter")
@SelectClasses({ SignInTests.class })
public class TestRunner {
    static {
        System.setProperty("env", System.getProperty("env", "local"));
        System.setProperty("browser", System.getProperty("browser", "chrome"));
        System.setProperty("baseUrl", System.getProperty("baseUrl", "https://www.greencity.cx.ua/"));
        System.setProperty("timeoutSeconds", System.getProperty("timeoutSeconds", "7"));
    }
}