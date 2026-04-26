package praktikum.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import praktikum.models.Order;

import static io.restassured.RestAssured.given;
import static praktikum.utils.ApiConstants.AUTH;
import static praktikum.utils.ApiConstants.ORDERS;

public class OrderClient {

    public Response createOrder(Order order, String token) {
        var request = given()
                .contentType(ContentType.JSON)
                .body(order);

        if (token != null) {
            request.header(AUTH, token);
        }

        return request.when().post(ORDERS);
    }
}