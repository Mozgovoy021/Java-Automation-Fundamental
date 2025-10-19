package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        long timeout = Long.parseLong(System.getProperty("timeoutSeconds", "10"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        this.js = (JavascriptExecutor) driver;

    }

    public void goTo(String url) {
        driver.get(url);
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    public String title() {
        return driver.getTitle();
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);

    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText()
                .trim();
    }

    protected String getAttribute(By locator, String name) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getAttribute(name);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void scrollIntoView(By locator) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", waitVisible(locator));
    }

    protected void jsClick(By locator) {
        js.executeScript("arguments[0].click();", waitVisible(locator));
    }

    protected void clickBody() {
        js.executeScript("document.body.click();");
    }


}





