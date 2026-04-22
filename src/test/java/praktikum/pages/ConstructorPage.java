package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConstructorPage extends BasePage {

    public ConstructorPage(WebDriver driver) {
        super(driver);
    }

    private By bunsTab = By.xpath(".//span[text()='Булки']");
    private By saucesTab = By.xpath(".//span[text()='Соусы']");
    private By fillingsTab = By.xpath(".//span[text()='Начинки']");
    private By activeTab = By.xpath(".//div[contains(@class,'tab_tab_type_current')]");

    @Step("Click Bun tab")
    public ConstructorPage clickBuns() {
        driver.findElement(bunsTab).click();
        return this;
    }

    @Step("Click Sauces tab")
    public ConstructorPage clickSauces() {
        driver.findElement(saucesTab).click();
        return this;
    }

    @Step("Click Fillings tab")
    public ConstructorPage clickFillings() {
        driver.findElement(fillingsTab).click();
        return this;
    }

    @Step("Check active tab is {tabName}")
    public boolean isTabActive(String tabName) {
        return driver.findElement(activeTab).getText().contains(tabName);
    }
}