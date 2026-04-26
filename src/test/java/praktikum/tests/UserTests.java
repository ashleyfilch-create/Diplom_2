package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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

    @Test
    @Story("Create unique user")
    @Description("User with unique email should be created successfully")
    public void createUniqueUserSuccess() {

        User user = UserGenerator.randomUser();

        String token = null;

        try {
            Response response = userClient.createUser(user);

            response.then()
                    .statusCode(SC_OK)
                    .body("success", equalTo(true))
                    .body("accessToken", notNullValue());

            token = response.path("accessToken");

        } finally {
            if (token != null) {
                userClient.deleteUser(token);
            }
        }
    }

    @Test
    @Story("Create existing user")
    @Description("Creating already registered user should return 409")
    public void createExistingUserFails() {

        User user = UserGenerator.randomUser();

        String token = null;

        try {
            Response createResponse = userClient.createUser(user);
            token = createResponse.path("accessToken");

            Response response = userClient.createUser(user);

            response.then()
                    .statusCode(SC_CONFLICT)
                    .body("message", equalTo("User already exists"));

        } finally {
            if (token != null) {
                userClient.deleteUser(token);
            }
        }
    }

    @Test
    @Story("Create user without required field")
    @Description("User without email should return 400 Bad Request")
    public void createUserWithoutEmailFails() {

        User user = new User(
                null,
                "123456",
                "test"
        );

        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", notNullValue());
    }
}