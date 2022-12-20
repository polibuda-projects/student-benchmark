package com.polibudaprojects.studentbenchmark.selenium.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginForm {
    private final WebElement usernameInput;
    private final WebElement passwordInput;
    private final WebElement submitButton;

    public LoginForm(WebDriver driver) throws NoSuchElementException {
        usernameInput = driver.findElement(By.xpath("//input[@name='usernameLog']"));
        passwordInput = driver.findElement(By.xpath("//input[@name='passwordLogin']"));
        submitButton = driver.findElement(By.xpath("//div/button[text()='Login']"));
    }

    public void clearInputs() {
        usernameInput.clear();
        passwordInput.clear();
    }

    public WebElement getUsernameInput() {
        return usernameInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }
}
