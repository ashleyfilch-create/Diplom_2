package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    private By emailField = By.xpath(".//input[@name='name']");
    private By restoreButton = By.xpath(".//button[text()='Восстановить']");

    @Step("Restore password for email: {email}")
    public ForgotPasswordPage restorePassword(String email) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(restoreButton).click();
        return this;
    }
}