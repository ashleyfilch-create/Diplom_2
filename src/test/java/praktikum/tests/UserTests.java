package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import praktikum.BaseTest;
import praktikum.client.UserClient;
import praktikum.models.User;
import praktikum.utils.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@Epic("Stellar Burgers API")
@Feature("User management")
@DisplayName("User tests")
public class UserTests extends BaseTest {

    private final UserClient userClient = new UserClient();

    private String token;

    @After
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }

    @Test
    @Story("Create unique user")
    @Description("User with unique email should be created successfully")
    public void createUniqueUserSuccess() {

        User user = UserGenerator.randomUser();

        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());

        token = response.path("accessToken");
    }

    @Test
    @Story("Create existing user")
    @Description("Creating already registered user should return 403")
    public void createExistingUserFails() {

        User user = UserGenerator.randomUser();

        Response createResponse = userClient.createUser(user);
        token = createResponse.path("accessToken");

        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @Story("Create user without email")
    @Description("User without email should return 403 Forbidden")
    public void createUserWithoutEmailFails() {

        User user = new User(null, "123456", "test");

        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("message", notNullValue());
    }

    @Test
    @Story("Create user without password")
    @Description("User without password should return 403 Forbidden")
    public void createUserWithoutPasswordFails() {

        User user = new User("test@email.com", null, "test");

        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("message", notNullValue());
    }

    @Test
    @Story("Create user without name")
    @Description("User without name should return 403 Forbidden")
    public void createUserWithoutNameFails() {

        User user = new User("test@email.com", "123456", null);

        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("message", notNullValue());
    }
}