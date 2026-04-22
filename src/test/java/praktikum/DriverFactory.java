package praktikum;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        }

        if (browser.equalsIgnoreCase("yandex")) {
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:/Program Files/Yandex/YandexBrowser/Application/browser.exe");
            return new ChromeDriver(options);
        }

        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }
}