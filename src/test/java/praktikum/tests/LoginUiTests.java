package praktikum.tests;

import io.qameta.allure.*;
import org.junit.Test;
import praktikum.BaseTestUi;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;

@Epic("UI Tests")
@Feature("Login")

public class LoginUiTests extends BaseTestUi {

    @Test
    @Story("Login via main page button")
    public void login_viaMainPage() {

        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.login("test@mail.com", "123456");
    }

    @Test
    @Story("Login via personal account")
    public void login_viaPersonalAccount() {

        MainPage main = new MainPage(driver);
        main.clickPersonalAccount();

        LoginPage login = new LoginPage(driver);
        login.login("test@mail.com", "123456");
    }
}