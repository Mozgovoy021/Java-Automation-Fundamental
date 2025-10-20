package services;

import org.openqa.selenium.WebDriver;
import pages.SignInPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final SignInPage page;

    public AuthService(WebDriver driver) {
        this.page = new SignInPage(driver);
    }

    public SignInPage openSignInPage() {
        log.info("Opening Sign In page");
        page.openHome();
        log.info("Sign In page opened");
        page.clickSignIn();
        return page;
    }

    public SignInPage login(String email, String password) {
        log.info("Login using email: {}, password: {}", email, password);
        openSignInPage();
        log.info("Email is: {}", email);
        page.typeEmail(email);
        log.info("Password is: {}", password);
        page.typePassword(password);
        log.info("Click Sign In button");
        page.submit();
        return page;
    }
}