package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import praktikum.BaseTest;
import praktikum.client.OrderClient;
import praktikum.client.UserClient;
import praktikum.models.Order;
import praktikum.models.User;
import praktikum.utils.UserGenerator;
import io.restassured.response.Response;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Stellar Burgers API")
@Feature("Orders")
@DisplayName("Order tests")
public class OrderTests extends BaseTest {

    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();

    @Test
    @Story("Create order with authorization")
    @Description("User with valid token can create order successfully")
    public void createOrder_withAuth_success() {

        User user = UserGenerator.randomUser();
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        String token = loginResponse.path("accessToken");

        Order order = new Order(List.of("ingredient1", "ingredient2"));

        Response response = orderClient.createOrder(order, token);

        response.then()
                .statusCode(SC_OK);

        userClient.deleteUser(token);
    }

    @Test
    @Story("Create order without authorization")
    @Description("Request without auth should return 401")
    public void createOrder_withoutAuth_fail() {

        Order order = new Order(List.of("ingredient1", "ingredient2"));

        Response response = orderClient.createOrder(order, null);

        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @Story("Create order without ingredients")
    @Description("Order without ingredients should return 400")
    public void createOrder_withoutIngredients_fail() {

        User user = UserGenerator.randomUser();
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        String token = loginResponse.path("accessToken");

        Order order = new Order(List.of());

        Response response = orderClient.createOrder(order, token);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));

        userClient.deleteUser(token);
    }

    @Test
    @Story("Create order with invalid ingredient hash")
    @Description("Invalid ingredient hash should return 400 Bad Request")
    public void createOrder_invalidHash_fail() {

        User user = UserGenerator.randomUser();
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        String token = loginResponse.path("accessToken");

        Order order = new Order(List.of("invalid_hash"));

        Response response = orderClient.createOrder(order, token);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("One or more ids provided are incorrect"));

        userClient.deleteUser(token);
    }
}