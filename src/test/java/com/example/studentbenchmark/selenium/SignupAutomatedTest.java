package com.example.studentbenchmark.selenium;

import com.example.studentbenchmark.selenium.forms.LoginForm;
import com.example.studentbenchmark.selenium.forms.SignupForm;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.example.studentbenchmark.TestConstants.*;

// IMPORTANT - This test requires chromedriver.exe
public class SignupAutomatedTest {

    private static final String SIGNUP_URL = "https://www.studentbenchmark.pl/signup";
    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    }

    @BeforeEach
    public void openPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(SIGNUP_URL);
    }

    @AfterEach
    public void closePage() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }

    @Test
    public void shouldGetSignupPage() {
        Assertions.assertDoesNotThrow(() -> {
            SignupForm signupForm = new SignupForm(driver);
            Assertions.assertNotNull(signupForm.getUsernameInput());
            Assertions.assertNotNull(signupForm.getEmailInput());
            Assertions.assertNotNull(signupForm.getPasswordInput());
            Assertions.assertNotNull(signupForm.getPasswordRepeatInput());
            Assertions.assertNotNull(signupForm.getTermsCheckBox());
            Assertions.assertNotNull(signupForm.getSubmitButton());
        });
    }

    @Test
    public void shouldSignup() {
        SignupForm signupForm = new SignupForm(driver);
        signupForm.clearInputs();

        signupForm.getUsernameInput().sendKeys(USER_NICKNAME);
        signupForm.getEmailInput().sendKeys(USER_EMAIL);
        signupForm.getPasswordInput().sendKeys(USER_PASSWORD);
        signupForm.getPasswordRepeatInput().sendKeys(USER_PASSWORD);
        signupForm.getTermsCheckBox().click();
        signupForm.getSubmitButton().click();

        String expectedUrl = "https://www.studentbenchmark.pl";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @ParameterizedTest
    @CsvSource({"us, Username is too short",
            "TooLongUsername#o8f0myI34jvrKGyqLHWtZGLfTDuPa7uUwRpwVtBPyBjA3PGhF, Username is too long"})
    public void shouldWarnUser_whenUsernameIsInvalid(String invalidUsername, String warningMessage) {
        SignupForm signupForm = new SignupForm(driver);
        signupForm.clearInputs();

        signupForm.getUsernameInput().sendKeys(invalidUsername);

        Assertions.assertTrue(driver.getPageSource().contains(warningMessage));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidEmails.csv")
    public void shouldWarnUser_whenEmailIsInvalid(String invalidEmail) {
        SignupForm signupForm = new SignupForm(driver);
        signupForm.clearInputs();

        signupForm.getEmailInput().sendKeys(invalidEmail);

        Assertions.assertTrue(driver.getPageSource().contains("Email is not valid"));
    }

    @Test
    public void shouldWarnUser_whenEmailIsTooLong() {
        String invalidEmail = "user@TooLongEmaildo8f0myI34jvrKGyqLHWtZGLfTDuPa7uUwRpwVtBPyBj.com";
        SignupForm signupForm = new SignupForm(driver);
        signupForm.clearInputs();

        signupForm.getEmailInput().sendKeys(invalidEmail);

        Assertions.assertTrue(driver.getPageSource().contains("Email is too long"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void shouldWarnUser_whenPasswordIsInvalid(String invalidPassword, String warningMessage) {
        SignupForm signupForm = new SignupForm(driver);
        signupForm.clearInputs();

        signupForm.getPasswordInput().sendKeys(invalidPassword);

        Assertions.assertTrue(driver.getPageSource().contains(warningMessage));
    }
}
