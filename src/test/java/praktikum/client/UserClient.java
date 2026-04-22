package praktikum.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import praktikum.models.User;

import static io.restassured.RestAssured.given;
import static praktikum.constants.ApiConstants.*;

public class UserClient {

    private String formatToken(String token) {
        if (token == null) return null;
        return token.startsWith("Bearer ") ? token : "Bearer " + token;
    }

    public Response createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .when()
                .post(REGISTER);
    }

    public Response loginUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .when()
                .post(LOGIN);
    }

    public Response deleteUser(String token) {

        String authHeader = formatToken(token);

        RequestSpecification request = given()
                .log().all();

        if (authHeader != null) {
            request.header(AUTH, authHeader);
        }

        return request.when().delete(USER);
    }
}