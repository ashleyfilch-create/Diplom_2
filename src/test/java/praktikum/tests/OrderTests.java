package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import praktikum.BaseTest;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static praktikum.constants.ApiConstants.ORDERS;

@Epic("Stellar Burgers API")
@Feature("Orders")
@DisplayName("Order tests")

public class OrderTests extends BaseTest {

    @Test
    @Story("Create order with authorization")
    @Description("User with valid token can create order successfully")
    public void createOrder_withAuth_success() {

        String token = "valid_token";

        Map<String, Object> body = Map.of(
                "ingredients", List.of("123", "456")
        );

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
                .when()
                .post(ORDERS)
                .then()
                .statusCode(200);
    }

    @Test
    @Story("Create order without authorization")
    @Description("Request without auth should return 401")
    public void createOrder_withoutAuth_fail() {

        Map<String, Object> body = Map.of(
                "ingredients", List.of("123", "456")
        );

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(ORDERS)
                .then()
                .statusCode(401);
    }

    @Test
    @Story("Create order without ingredients")
    @Description("Order without ingredients should return 400")
    public void createOrder_withoutIngredients_fail() {

        Map<String, Object> body = Map.of(
                "ingredients", List.of()
        );

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(ORDERS)
                .then()
                .statusCode(400);
    }

    @Test
    @Story("Create order with invalid ingredient hash")
    @Description("Invalid ingredient hash should return 400 Bad Request")
    public void createOrder_invalidHash_fail() {

        Map<String, Object> body = Map.of(
                "ingredients", List.of("invalid_hash")
        );

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(ORDERS)
                .then()
                .statusCode(400);
    }
}