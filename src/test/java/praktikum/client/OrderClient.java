package praktikum.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import praktikum.models.Order;

import static io.restassured.RestAssured.given;
import static praktikum.constants.ApiConstants.AUTH;
import static praktikum.constants.ApiConstants.ORDERS;

public class OrderClient {

    public Response createOrder(Order order, String token) {

        RequestSpecification spec = given()
                .contentType(ContentType.JSON)
                .body(order)
                .log().all();

        if (token != null) {
            spec.header(AUTH, "Bearer " + token);
        }

        return spec.when().post(ORDERS);
    }
}