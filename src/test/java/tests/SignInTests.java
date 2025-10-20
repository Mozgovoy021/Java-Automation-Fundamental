package tests;

import data.SignInData;
import data.SignInNegativeData;
import framework.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.SignInPage;
import services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


public class SignInTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(SignInTests.class);
    private SignInPage signInPage;

    @BeforeEach
    void setUp() {
        signInPage = new SignInPage(driver);
    }

    @Test
    @DisplayName("Verify page title is 'GreenCity'")
    void verifyTitle() {
        signInPage.openHome();
        assertEquals("GreenCity", signInPage.title());
    }

    @Test
    @DisplayName("Verify welcome text and details text on Sign In page")
    void verifyWelcomeText() {
        signInPage.openHome();
        signInPage.clickSignIn();
        assertEquals("Welcome back!", signInPage.getWelcomeText());
        assertTrue(signInPage.getDetailsText().contains("Please enter your details to sign in."), "Please enter your details to sign in.");
    }

    @ParameterizedTest(name = "[{index}] valid login -> {0}")
    @MethodSource("data.repo.SignInDataRepo#positiveData")
    void verifySuccessfulLogin(SignInData td) {
        var auth = new AuthService(driver);
        var pageAfter = auth.login(td.email(), td.password());
        assertEquals(td.expectedUrl(), pageAfter.currentUrl());
    }

    @ParameterizedTest(name = "[{index}] invalid -> {0}")
    @MethodSource("data.repo.SignInDataRepo#negativeFromCsv")
    void verifyInvalidCredentials(SignInNegativeData td) {
        var page = new AuthService(driver).openSignInPage();
        page.typeEmail(td.email());
        page.typePassword(td.password());
        page.blurFields();

        assertFalse(page.isSubmitEnabled(), "Submit should be disabled for invalid input");
        if (!td.expectedEmailError().isEmpty()) assertEquals(td.expectedEmailError(), page.getEmailErrorMessage());
        if (!td.expectedPasswordError().isEmpty())
            assertEquals(td.expectedPasswordError(), page.getPasswordErrorMessage());
    }

    @Test
    @DisplayName("Sign In disabled until both fields are filled")
    void verifySignInButtonDisabledInitially() {
        signInPage.openHome();
        signInPage.clickSignIn();
        assertFalse(signInPage.isSubmitEnabled(), "Submit should be disabled initially");
    }

    @Test
    @DisplayName("Errors for wrong email & password")
    void verifyEmptyFieldsErrorMessages() {
        signInPage.openHome();
        signInPage.clickSignIn();
        signInPage.typeEmail("WrongEmail");
        signInPage.typePassword("Error!");
        signInPage.blurFields();
        assertTrue(signInPage.isEmailErrorVisible());
        assertTrue(signInPage.isPasswordErrorVisible());
    }

}