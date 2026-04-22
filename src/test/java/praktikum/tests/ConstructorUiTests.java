package praktikum.tests;

import io.qameta.allure.*;
import org.junit.Assert;
import org.junit.Test;
import praktikum.BaseTestUi;
import praktikum.pages.ConstructorPage;

@Epic("UI Tests")
@Feature("Constructor")

public class ConstructorUiTests extends BaseTestUi {

    @Test
    @Story("Switch tabs in constructor")
    public void checkTabs() {

        ConstructorPage constructor = new ConstructorPage(driver);

        constructor.clickSauces();
        Assert.assertTrue("Sauces tab is not active",
                constructor.isTabActive("Соусы"));

        constructor.clickFillings();
        Assert.assertTrue("Fillings tab is not active",
                constructor.isTabActive("Начинки"));

        constructor.clickBuns();
        Assert.assertTrue("Buns tab is not active",
                constructor.isTabActive("Булки"));
    }
}