package com.example.studentbenchmark.selenium;

import com.example.studentbenchmark.selenium.forms.LoginForm;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.example.studentbenchmark.TestConstants.USER_EMAIL;
import static com.example.studentbenchmark.TestConstants.USER_PASSWORD;

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
            Assertions.assertNotNull(loginForm.getEmailInput());
            Assertions.assertNotNull(loginForm.getPasswordInput());
            Assertions.assertNotNull(loginForm.getSubmitButton());
        });
    }

    @Test
    public void shouldLogin() {
        LoginForm loginForm = new LoginForm(driver);
        loginForm.clearInputs();

        loginForm.getEmailInput().sendKeys(USER_EMAIL);
        loginForm.getPasswordInput().sendKeys(USER_PASSWORD);
        loginForm.getSubmitButton().click();

        String expectedUrl = "https://www.studentbenchmark.pl";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
