package com.example.studentbenchmark.selenium.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginForm {
    private final WebElement emailInput;
    private final WebElement passwordInput;
    private final WebElement submitButton;

    public LoginForm(WebDriver driver) throws NoSuchElementException {
        emailInput = driver.findElement(By.xpath("//input[@name='emailLog']"));
        passwordInput = driver.findElement(By.xpath("//input[@name='passwordLogin']"));
        submitButton = driver.findElement(By.xpath("//button[text()='Login']"));
    }

    public void clearInputs() {
        emailInput.clear();
        passwordInput.clear();
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }
}
