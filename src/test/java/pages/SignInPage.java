package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {

    private final By signInButton = By.cssSelector(".ubs-header-sign-in.ng-star-inserted");
    private final By welcomeText = By.cssSelector("div[class='container'] div h1");
    private final By signInDetailsText = By.cssSelector("div[class='container'] div h2");
    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By signInSubmitButton = By.cssSelector("button[class='ubsStyle']");
    private final By errorPassword = By.cssSelector("div[id='pass-err-msg'] div[class='margining ng-star-inserted']");
    private final By errorEmail = By.cssSelector("app-error[controlname='email'] div[class='margining ng-star-inserted']");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void openHome() {
        goTo(System.getProperty("baseUrl", "https://www.greencity.cx.ua/"));
    }

    public void clickSignIn() {
        click(signInButton);
    }

    public void typeEmail(String email) {
        type(emailInput, email);
    }

    public void typePassword(String password) {
        type(passwordInput, password);
    }

    public void submit() {
        click(signInSubmitButton);
    }

    public void login(String email, String password) {
        typeEmail(email);
        typePassword(password);
        submit();
    }

    public String getWelcomeText() {
        return getText(welcomeText);
    }

    public String getDetailsText() {
        return getText(signInDetailsText);
    }

    public boolean isEmailErrorVisible() {
        return isDisplayed(errorEmail);
    }

    public boolean isPasswordErrorVisible() {
        return isDisplayed(errorPassword);
    }

    public boolean isSubmitEnabled() {
        String disabled = getAttribute(signInSubmitButton, "disabled");
        return disabled == null || disabled.isEmpty();
    }

    public String getEmailErrorMessage() {
        return getText(errorEmail);
    }

    public String getPasswordErrorMessage() {
        return getText(errorPassword);
    }

    public void blurFields() {
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
    }

    public void scrollToSubmit() {
        scrollIntoView(signInSubmitButton);
    }

    public void jsSubmit() {
        jsClick(signInSubmitButton);
    }

}


