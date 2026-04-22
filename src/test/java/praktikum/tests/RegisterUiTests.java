package praktikum.tests;

import io.qameta.allure.*;
import org.junit.Test;
import praktikum.BaseTest;
import praktikum.BaseTestUi;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;

@Epic("UI Tests")
@Feature("Registration")

public class RegisterUiTests extends BaseTestUi {

    @Test
    @Story("Successful registration")
    public void register_success() {

        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.goToRegister();

        RegisterPage register = new RegisterPage(driver);
        register.register("test@mail.com", "123456");
    }

    @Test
    @Story("Invalid password registration")
    public void register_invalidPassword() {

        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.goToRegister();

        RegisterPage register = new RegisterPage(driver);
        register.register("test@mail.com", "123");
    }
}