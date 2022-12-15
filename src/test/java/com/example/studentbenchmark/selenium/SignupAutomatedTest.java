package com.example.studentbenchmark.selenium;

import com.example.studentbenchmark.selenium.forms.SignupForm;
import org.junit.jupiter.api.*;
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
}
