package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseTest;
import praktikum.client.UserClient;
import praktikum.models.User;
import praktikum.utils.UserGenerator;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("Тесты авторизации пользователя")
public class LoginTests extends BaseTest {
    private final UserClient userClient = new UserClient();
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.randomUser();
        Response response = userClient.createUser(user);
        accessToken = response.path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.handleUserData("DELETE", null, accessToken);
        }
    }

    @Test
    @DisplayName("Успешный вход в систему")
    @Description("Проверка авторизации под существующим пользователем с корректным логином и паролем")
    public void loginSuccess() {
        userClient.loginUser(user)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Проверка получения ошибки 401 при попытке входа с корректным email, но неверным паролем")
    public void loginWithWrongPasswordFail() {
        User wrongUser = new User(user.getEmail(), "wrong_pass_123", user.getName());

        userClient.loginUser(wrongUser)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }
}