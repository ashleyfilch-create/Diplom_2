package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends BasePage {

    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private By personalAccount = By.xpath(".//p[text()='Личный Кабинет']");
    private By constructorHeader = By.xpath(".//h1[text()='Соберите бургер']");

    @Step("Click Login button")
    public LoginPage clickLoginButton() {
        driver.findElement(loginButton).click();
        return new LoginPage(driver);
    }

    @Step("Click Personal Account button")
    public LoginPage clickPersonalAccount() {
        driver.findElement(personalAccount).click();
        return new LoginPage(driver);
    }

    @Step("Check Main page is loaded")
    public boolean isLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(constructorHeader));
        return driver.findElement(constructorHeader).isDisplayed();
    }
}