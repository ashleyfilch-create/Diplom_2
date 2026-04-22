package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage extends BasePage {

    private WebDriverWait wait;

    public RegisterPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private By nameField = By.xpath(".//input[@name='name']");
    private By emailField = By.xpath(".//input[@name='name']"); // ⚠ лучше заменить на реальный locator email
    private By passwordField = By.xpath(".//input[@name='Пароль']");
    private By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private By errorMessage = By.xpath(".//p[contains(@class,'input__error')]");

    @Step("Register user with email: {email}")
    public void register(String email, String password) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));

        driver.findElement(nameField).sendKeys("Test");
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(registerButton).click();
    }

    @Step("Check password error is visible")
    public boolean isPasswordErrorVisible() {
        return driver.findElement(errorMessage).isDisplayed();
    }
}