package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.SignInPage;

public class SignInTests {
    private WebDriver driver;
    private SignInPage signInPage;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        signInPage = new SignInPage(driver);
    }

    @Test
    @DisplayName("Verify page title is 'GreenCity'")
    void verifyTitle() {
        signInPage.openHome();
        Assertions.assertEquals("GreenCity", signInPage.title());
    }

    @Test
    @DisplayName("Verify welcome text and details text on Sign In page")
    void verifyWelcomeText() {
        signInPage.openHome();
        signInPage.clickSignIn();
        Assertions.assertEquals("Welcome back!", signInPage.getWelcomeText());
        Assertions.assertTrue(signInPage.getDetailsText().contains("Please enter your details to sign in."), "Please enter your details to sign in.");
    }

    // Пароль не ховав, бо інакше не передам вам креди
    @Test
    @DisplayName("Verify successful login with valid credentials")
    void verifySuccessfulLogin() {
        signInPage.openHome();
        signInPage.clickSignIn();
        signInPage.login("dehoye6184@aupvs.com", "Test55555@");
        signInPage.submit();
        Assertions.assertEquals("https://www.greencity.cx.ua/#/ubs", signInPage.currentUrl());
    }

    @ParameterizedTest
    @CsvSource({"Email, Password", "Email2, Password2",})
    @DisplayName("Parameterized test – invalid email/password pairs show errors")
    void verifyInvalidCredentials(String email, String password) {
        signInPage.openHome();
        signInPage.clickSignIn();
        signInPage.typeEmail(email);
        signInPage.typePassword(password);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        signInPage.isSubmitEnabled();
        Assertions.assertTrue(signInPage.isEmailErrorVisible() || signInPage.isPasswordErrorVisible());
    }

    @Test
    @DisplayName("Verify 'Sign In' button is disabled until both fields are filled")
    void verifySignInButtonDisabledInitially() {
        signInPage.openHome();
        signInPage.clickSignIn();
        Assertions.assertFalse(signInPage.isSubmitEnabled(), "Submit should be disabled initially");
    }

    @Test
    @DisplayName("Verify error messages for wrong email and password")
    void verifyEmptyFieldsErrorMessages() {
        signInPage.openHome();
        signInPage.clickSignIn();
        signInPage.typeEmail("WrongEmail");
        signInPage.typePassword("Error!");
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        Assertions.assertTrue(signInPage.isEmailErrorVisible());
        Assertions.assertTrue(signInPage.isPasswordErrorVisible());
    }


    @AfterEach
    public void teardown() {
        driver.quit();
    }

}
