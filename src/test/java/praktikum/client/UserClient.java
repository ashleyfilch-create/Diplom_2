package praktikum.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import praktikum.models.User;

import static io.restassured.RestAssured.given;
import static praktikum.utils.ApiConstants.*;

public class UserClient {

    @Step("Создание пользователя")
    public Response createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(REGISTER);
    }

    @Step("Логин пользователя")
    public Response loginUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(LOGIN);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String token) {
        return given()
                .header(AUTH, token)
                .when()
                .delete(USER);
    }
}