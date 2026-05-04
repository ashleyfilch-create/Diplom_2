package praktikum.client;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import praktikum.models.User;
import praktikum.utils.ApiConstants;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class UserClient {

    @Step("Регистрация пользователя")
    public Response createUser(User user) {
        return given().filter(new AllureRestAssured())
                .header("Content-type", ApiConstants.CONTENT_TYPE)
                .body(user)
                .when()
                .post(ApiConstants.BASE_URL + ApiConstants.REGISTER);
    }

    @Step("Логин пользователя")
    public Response loginUser(User user) {
        return given().filter(new AllureRestAssured())
                .header("Content-type", ApiConstants.CONTENT_TYPE)
                .body(user)
                .when()
                .post(ApiConstants.BASE_URL + ApiConstants.LOGIN);
    }

    @Step("Выход из системы (Logout)")
    public Response logout(String refreshToken) {
        return given().filter(new AllureRestAssured())
                .header("Content-type", ApiConstants.CONTENT_TYPE)
                .body(Map.of("token", refreshToken))
                .when()
                .post(ApiConstants.BASE_URL + ApiConstants.LOGOUT);
    }

    @Step("Универсальный метод для работы с данными пользователя (GET, PATCH, DELETE)")
    public Response handleUserData(String method, User user, String token) {
        RequestSpecification spec = given().filter(new AllureRestAssured())
                .header("Content-type", ApiConstants.CONTENT_TYPE);

        if (token != null && !token.isEmpty()) {
            spec.header("Authorization", token);
        }

        if (user != null) {
            spec.body(user);
        }

        String url = ApiConstants.BASE_URL + ApiConstants.USER;

        switch (method.toUpperCase()) {
            case "GET":
                return spec.get(url);
            case "PATCH":
                return spec.patch(url);
            case "DELETE":
                return spec.delete(url);
            default:
                throw new IllegalArgumentException("Метод " + method + " не поддерживается");
        }
    }
}