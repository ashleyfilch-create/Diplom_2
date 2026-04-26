package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import praktikum.BaseTest;
import praktikum.client.UserClient;
import praktikum.models.User;
import praktikum.utils.UserGenerator;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Stellar Burgers API")
@Feature("Authentication")
@DisplayName("Login tests")
public class LoginTests extends BaseTest {

    private final UserClient userClient = new UserClient();

    @Test
    @Story("Successful login")
    @Description("Login with valid credentials should return access token")
    public void loginSuccess() {

        User user = UserGenerator.randomUser();

        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);

        loginResponse.then()
                .statusCode(SC_OK)
                .body("accessToken", notNullValue());

        String token = loginResponse.path("accessToken");
        userClient.deleteUser(token);
    }

    @Test
    @Story("Login with invalid credentials")
    @Description("Login with wrong credentials should return 401 and error message")
    public void loginWrongCredentials_fail() {

        User user = UserGenerator.randomUser();

        userClient.createUser(user);

        User wrongUser = new User(
                user.getEmail(),
                "incorrectPassword",
                user.getName()
        );

        Response response = userClient.loginUser(wrongUser);

        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }
}