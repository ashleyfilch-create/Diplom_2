package praktikum;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static praktikum.constants.ApiConstants.BASE_URL;

public class BaseTestUi {

    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver(System.getProperty("browser", "chrome"));
        driver.manage().window().maximize();
        openSite();
    }

    @Step("Open Stellar Burgers main page")
    public void openSite() {
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}