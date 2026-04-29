package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
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

    private User user;
    private String token;

    @Before
    public void setUp() {
        user = UserGenerator.randomUser();
        userClient.createUser(user);
    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }

    @Test
    @Story("Successful login")
    public void loginSuccess() {

        Response response = userClient.loginUser(user);

        response.then()
                .statusCode(SC_OK)
                .body("accessToken", notNullValue());

        token = response.path("accessToken");
    }

    @Test
    @Story("Login with wrong email")
    public void loginWrongEmailFail() {

        User wrongUser = new User(
                "wrong_" + user.getEmail(),
                user.getPassword(),
                user.getName()
        );

        Response response = userClient.loginUser(wrongUser);

        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @Story("Login with wrong password")
    public void loginWrongPasswordFail() {

        User wrongUser = new User(
                user.getEmail(),
                "wrongPassword",
                user.getName()
        );

        Response response = userClient.loginUser(wrongUser);

        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }
}