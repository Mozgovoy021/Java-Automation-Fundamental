package services;

import org.openqa.selenium.WebDriver;
import pages.SignInPage;

public class AuthService {
    private final SignInPage page;

    public AuthService(WebDriver driver) {
        this.page = new SignInPage(driver);
    }

    public SignInPage openSignInPage() {
        page.openHome();
        page.clickSignIn();
        return page;
    }

    public SignInPage login(String email, String password) {
        openSignInPage();
        page.typeEmail(email);
        page.typePassword(password);
        page.submit();
        return page;
    }
}