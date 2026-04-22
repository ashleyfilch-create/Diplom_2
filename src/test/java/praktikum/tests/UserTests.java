package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import praktikum.BaseTest;
import praktikum.models.User;
import org.junit.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static praktikum.constants.ApiConstants.REGISTER;

@Epic("Stellar Burgers API")
@Feature("User management")
@DisplayName("User tests")

public class UserTests extends BaseTest {

    @Test
    @Story("Create unique user")
    @Description("User with unique email should be created successfully")
    public void createUniqueUser_success() {

        User user = new User(
                "user_" + UUID.randomUUID() + "@mail.com",
                "123456",
                "test"
        );

        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(REGISTER)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());
    }

    @Test
    @Story("Create existing user")
    @Description("Creating already registered user should return 409")
    public void createExistingUser_fails() {

        String email = "user_" + UUID.randomUUID() + "@mail.com";

        User user = new User(email, "123456", "test");

        // First registration
        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(REGISTER)
                .then()
                .statusCode(200);

        // Second registration
        given()
                .contentType("application/json")
                .body(new User(email, "123456", "test"))
                .when()
                .post(REGISTER)
                .then()
                .statusCode(409)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @Story("Create user without required field")
    @Description("User without email should return 400 Bad Request")
    public void createUserWithoutEmail_fails() {

        User user = new User(null, "123456", "test");

        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(REGISTER)
                .then()
                .statusCode(400)
                .body("message", notNullValue());
    }
}