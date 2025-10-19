package data;

public record SignInNegativeData(String email, String password, String expectedEmailError,
                                 String expectedPasswordError) {
}