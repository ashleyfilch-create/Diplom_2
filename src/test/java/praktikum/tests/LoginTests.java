package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseTest;
import praktikum.client.UserClient;
import praktikum.models.User;
import praktikum.utils.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@Epic("Stellar Burgers API")
@Feature("Authentication")
@DisplayName("Login tests")
public class LoginTests extends BaseTest {

    private final UserClient userClient = new UserClient();

    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.randomUser();
        userClient.createUser(user);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @Story("Successful login")
    public void loginSuccess() {

        Response response = userClient.loginUser(user);

        response.then()
                .statusCode(SC_OK)
                .body("accessToken", notNullValue());

        accessToken = response.path("accessToken");
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