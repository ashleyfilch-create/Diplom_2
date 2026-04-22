package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private By emailField = By.xpath(".//input[@name='name']");
    private By passwordField = By.xpath(".//input[@name='Пароль']");
    private By loginButton = By.xpath(".//button[text()='Войти']");
    private By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private By forgotPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");

    @Step("Login with email: {email}")
    public LoginPage login(String email, String password) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();

        return this;
    }

    @Step("Go to Register page")
    public RegisterPage goToRegister() {
        driver.findElement(registerLink).click();
        return new RegisterPage(driver);
    }

    @Step("Go to Forgot Password page")
    public ForgotPasswordPage goToForgotPassword() {
        driver.findElement(forgotPasswordLink).click();
        return new ForgotPasswordPage(driver);
    }
}