package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import praktikum.BaseTest;
import praktikum.models.User;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static praktikum.constants.ApiConstants.LOGIN;

@Epic("Stellar Burgers API")
@Feature("Authentication")
@DisplayName("Login tests")

public class LoginTests extends BaseTest {

    @Test
    @Story("Successful login")
    @Description("Login with valid credentials should return access token")
    public void login_success() {

        User user = new User("user@mail.com", "123456", null);

        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(LOGIN)
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Test
    @Story("Login with invalid credentials")
    @Description("Login with wrong credentials should return 401 Unauthorized")
    public void login_wrongCredentials_fail() {

        User user = new User("wrong@mail.com", "wrong", null);

        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(LOGIN)
                .then()
                .statusCode(401);
    }
}