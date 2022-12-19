package com.polibudaprojects.studentbenchmark.selenium.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignupForm {
    private final WebElement usernameInput;
    private final WebElement emailInput;
    private final WebElement passwordInput;
    private final WebElement passwordRepeatInput;
    private final WebElement termsCheckBox;
    private final WebElement submitButton;

    public SignupForm(WebDriver driver) throws NoSuchElementException {
        usernameInput = driver.findElement(By.xpath("//input[@name='usernameLog']"));
        emailInput = driver.findElement(By.xpath("//input[@name='emailLog']"));
        passwordInput = driver.findElement(By.xpath("//input[@name='passwordRegister']"));
        passwordRepeatInput = driver.findElement(By.xpath("//input[@name='passwordRegisterRepeat']"));
        termsCheckBox = driver.findElement(By.xpath("//input[@name='terms']"));
        submitButton = driver.findElement(By.xpath("//button[text()='Sign up']"));
    }

    public void clearInputs() {
        usernameInput.clear();
        emailInput.clear();
        passwordInput.clear();
        passwordRepeatInput.clear();
    }

    public WebElement getUsernameInput() {
        return usernameInput;
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getPasswordRepeatInput() {
        return passwordRepeatInput;
    }

    public WebElement getTermsCheckBox() {
        return termsCheckBox;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }
}
