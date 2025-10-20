package framework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith(LoggingExtension.class)
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