package com.polibudaprojects.studentbenchmark.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

// IMPORTANT - This test requires chromedriver.exe
public class MainPageAutomatedTest {

    public static final String MAIN_URL = "https://www.studentbenchmark.pl";
    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    }

    @BeforeEach
    public void openPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(MAIN_URL);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @AfterEach
    public void closePage() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }

    @Test
    public void shouldOpenTestsPage() {
        WebElement navButton = driver.findElement(By.xpath("//button[text()='Get started']"));
        navButton.click();

        String expectedUrl = MAIN_URL + "/tests";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        Assertions.assertTrue(driver.getPageSource().contains("sequence memory"));
        Assertions.assertTrue(driver.getPageSource().contains("visual memory"));
        Assertions.assertTrue(driver.getPageSource().contains("verbal memory"));
        Assertions.assertTrue(driver.getPageSource().contains("number memory"));
    }

    @Test
    public void shouldOpenLoginPage() {
        WebElement navButton = driver.findElement(By.xpath("//button[text()='Login']"));
        navButton.click();

        String expectedUrl = MAIN_URL + "/login";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Assertions.assertNotNull(driver.findElement(By.xpath("//h1[text()='Log in']")));
    }

    @Test
    public void shouldOpenSignupPage() {
        WebElement navButton = driver.findElement(By.xpath("//button[text()='Sign up']"));
        navButton.click();

        String expectedUrl = MAIN_URL + "/signup";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Assertions.assertNotNull(driver.findElement(By.xpath("//h1[text()='Sign up']")));
    }

    @Test
    public void shouldOpenHomePage() {
        driver.get(MAIN_URL + "/dashboard");
        WebElement navButton = driver.findElement(By.xpath("//span[text()='Home']"));
        navButton.click();

        String expectedUrl = MAIN_URL + "/";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void shouldOpenDashboardPage() {
        WebElement navButton = driver.findElement(By.xpath("//span[text()='Dashboard']"));
        navButton.click();

        String expectedUrl = MAIN_URL + "/dashboard";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void shouldOpenSupportPage() {
        WebElement navButton = driver.findElement(By.xpath("//span[text()='Support']"));
        navButton.click();

        String expectedUrl = MAIN_URL + "/support";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void shouldOpenDonatePage() {
        WebElement navButton = driver.findElement(By.xpath("//span[text()='Donate']"));
        navButton.click();

        String expectedUrl = "https://www.buymeacoffee.com/polibudaproject";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
