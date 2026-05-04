package praktikum.client;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import praktikum.models.Order;
import praktikum.utils.ApiConstants;
import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Создание нового заказа")
    public Response createOrder(Order order, String token) {
        var spec = given().filter(new AllureRestAssured())
                .header("Content-type", ApiConstants.CONTENT_TYPE);

        if (token != null && !token.isEmpty()) {
            spec.header("Authorization", token);
        }

        return spec.body(order)
                .when()
                .post(ApiConstants.BASE_URL + ApiConstants.ORDERS);
    }

    @Step("Получение списка заказов пользователя")
    public Response getUserOrders(String token) {
        var spec = given().filter(new AllureRestAssured())
                .header("Content-type", ApiConstants.CONTENT_TYPE);

        if (token != null && !token.isEmpty()) {
            spec.header("Authorization", token);
        }

        return spec.when()
                .get(ApiConstants.BASE_URL + ApiConstants.ORDERS);
    }
}