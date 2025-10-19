package framework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    void setUpBrowser() {
        driver = WebDriverFactory.create(System.getProperty("browser", "chrome"));
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}