package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
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
        signInPage = new SignInPage(driver);
        driver.manage().window().maximize();

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
    @ParameterizedTest(name = "[{index}] valid login -> {0}")
    @CsvSource({
            "dehoye6184@aupvs.com,Test55555@",
            "dehoye6184@aupvs.com,Test55555@" // Дубль бо лінь створювати ще один реальний акаунт
    })
    @DisplayName("Login positive: valid email/password pairs")
    void verifySuccessfulLogin(String email, String password) {
        signInPage.openHome();
        signInPage.clickSignIn();
        signInPage.login(email, password);
        signInPage.scrollToSubmit();
        signInPage.jsSubmit();

        Assertions.assertEquals("https://www.greencity.cx.ua/#/ubs", signInPage.currentUrl());
    }

    @ParameterizedTest(name = "[{index}] invalid -> {0} / {1}")
    @CsvFileSource(resources = "/login-negative.csv", numLinesToSkip = 1)
    @DisplayName("Login negative: exact error messages (CsvFileSource)")
    void verifyInvalidCredentials(String email,
                                  String password,
                                  String expectedEmailError,
                                  String expectedPasswordError) {
        signInPage.openHome();
        signInPage.clickSignIn();
        signInPage.typeEmail(email);
        signInPage.typePassword(password);
        signInPage.blurFields();

        Assertions.assertFalse(signInPage.isSubmitEnabled(), "Submit should be disabled for invalid input");

        if (!expectedEmailError.isEmpty()) {
            Assertions.assertEquals(expectedEmailError, signInPage.getEmailErrorMessage());
        }
        if (!expectedPasswordError.isEmpty()) {
            Assertions.assertEquals(expectedPasswordError, signInPage.getPasswordErrorMessage());
        }
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
        signInPage.blurFields();
        Assertions.assertTrue(signInPage.isEmailErrorVisible());
        Assertions.assertTrue(signInPage.isPasswordErrorVisible());
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }


}
