package praktikum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
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

    private User user;
    private String token;

    @Before
    public void setUp() {
        user = UserGenerator.randomUser();
        userClient.createUser(user);

        Response loginResponse = userClient.loginUser(user);
        token = loginResponse.path("accessToken");
    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }

    @Test
    @Story("Create order with authorization")
    public void createOrderWithAuthSuccess() {

        Order order = new Order(List.of("ingredient1", "ingredient2"));

        Response response = orderClient.createOrder(order, token);

        response.then()
                .statusCode(SC_OK);
    }

    @Test
    @Story("Create order without authorization")
    public void createOrderWithoutAuthFail() {

        Order order = new Order(List.of("ingredient1", "ingredient2"));

        Response response = orderClient.createOrder(order, null);

        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @Story("Create order without ingredients")
    public void createOrderWithoutIngredientsFail() {

        Order order = new Order(List.of());

        Response response = orderClient.createOrder(order, token);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @Story("Create order with invalid ingredient hash")
    public void createOrderInvalidHashFail() {

        Order order = new Order(List.of("invalid_hash"));

        Response response = orderClient.createOrder(order, token);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("One or more ids provided are incorrect"));
    }
}