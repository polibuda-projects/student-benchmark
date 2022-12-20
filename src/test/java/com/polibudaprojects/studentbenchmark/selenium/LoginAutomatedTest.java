package com.polibudaprojects.studentbenchmark.selenium;

import com.polibudaprojects.studentbenchmark.selenium.forms.LoginForm;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static com.polibudaprojects.studentbenchmark.TestConstants.USER_EMAIL;
import static com.polibudaprojects.studentbenchmark.TestConstants.USER_PASSWORD;

// IMPORTANT - This test requires chromedriver.exe
public class LoginAutomatedTest {

    public static final String LOGIN_URL = "https://www.studentbenchmark.pl/login";
    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    }

    @BeforeEach
    public void openPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @AfterEach
    public void closePage() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }

    @Test
    public void shouldGetLoginPage() {
        Assertions.assertDoesNotThrow(() -> {
            LoginForm loginForm = new LoginForm(driver);
            Assertions.assertNotNull(loginForm.getUsernameInput());
            Assertions.assertNotNull(loginForm.getPasswordInput());
            Assertions.assertNotNull(loginForm.getSubmitButton());
        });
    }

    @Test
    public void shouldLogin() {
        LoginForm loginForm = new LoginForm(driver);
        loginForm.clearInputs();

        loginForm.getUsernameInput().sendKeys(USER_EMAIL);
        loginForm.getPasswordInput().sendKeys(USER_PASSWORD);
        loginForm.getSubmitButton().click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String expectedUrl = "https://www.studentbenchmark.pl/";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void shouldNotLogin_withoutEmail() {
        LoginForm loginForm = new LoginForm(driver);
        loginForm.clearInputs();

        loginForm.getPasswordInput().sendKeys(USER_PASSWORD);
        loginForm.getSubmitButton().click();

        String expectedUrl = "https://www.studentbenchmark.pl/login";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void shouldNotLogin_withoutPassword() {
        LoginForm loginForm = new LoginForm(driver);
        loginForm.clearInputs();

        loginForm.getUsernameInput().sendKeys(USER_EMAIL);
        loginForm.getSubmitButton().click();

        String expectedUrl = "https://www.studentbenchmark.pl/login";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @ParameterizedTest
    @CsvSource({"us, Username is too short",
            "TooLongUsername#o8f0myI34jvrKGyqLHWtZGLfTDuPa7uUwRpwVtBPyBjA3PGhF, Username is too long"})
    public void shouldWarnUser_whenUsernameIsInvalid(String invalidUsername, String warningMessage) {
        LoginForm loginForm = new LoginForm(driver);
        loginForm.clearInputs();

        loginForm.getUsernameInput().sendKeys(invalidUsername);

        Assertions.assertTrue(driver.getPageSource().contains(warningMessage));
    }
}
