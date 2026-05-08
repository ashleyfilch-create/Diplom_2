package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseTest;
import praktikum.client.UserClient;
import praktikum.models.User;
import praktikum.utils.UserGenerator;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("Тесты управления пользователями")
public class UserTests extends BaseTest {
    private final UserClient userClient = new UserClient();
    private String accessToken;
    private User user;

    @Before
    public void setUp() {
        user = UserGenerator.randomUser();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.handleUserData("DELETE", null, accessToken);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка успешного создания нового пользователя с валидными данными")
    public void createUserSuccess() {
        userClient.createUser(user)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));

        accessToken = userClient.loginUser(user).path("accessToken");
    }

    @Test
    @DisplayName("Создание дубликата пользователя")
    @Description("Проверка ошибки при попытке регистрации пользователя с уже существующим email")
    public void createDuplicateUserFail() {
        userClient.createUser(user);
        userClient.createUser(user)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Обновление имени авторизованного пользователя")
    @Description("Проверка возможности изменения персональных данных при наличии валидного токена")
    public void updateUserDataSuccess() {
        var resp = userClient.createUser(user);
        accessToken = resp.path("accessToken");

        User updatedUser = new User(user.getEmail(), user.getPassword(), "Updated Name");

        userClient.handleUserData("PATCH", updatedUser, accessToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("user.name", equalTo("Updated Name"));
    }

    @Test
    @DisplayName("Изменение данных без авторизации")
    @Description("Проверка отказа в доступе при попытке обновить данные пользователя без передачи токена")
    public void updateUserDataWithoutAuthFail() {
        userClient.createUser(user);
        User updatedUser = new User(user.getEmail(), user.getPassword(), "Ghost Name");

        userClient.handleUserData("PATCH", updatedUser, null)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }
}