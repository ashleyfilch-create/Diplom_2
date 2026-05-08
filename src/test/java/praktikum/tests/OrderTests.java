package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseTest;
import praktikum.client.OrderClient;
import praktikum.client.UserClient;
import praktikum.models.Order;
import praktikum.utils.UserGenerator;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("Тесты создания заказов")
public class OrderTests extends BaseTest {
    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();
    private String accessToken;

    @Before
    public void setUp() {
        var user = UserGenerator.randomUser();
        userClient.createUser(user);
        Response loginResponse = userClient.loginUser(user);
        accessToken = loginResponse.path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.handleUserData("DELETE", null, accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    @Description("Успешное оформление заказа при наличии токена и валидных ингредиентов")
    public void createOrderSuccess() {
        List<String> ingredients = List.of("60d3b41abdacab0026a733c6", "609646e4dc916e00276b2870");
        Order order = new Order(ingredients);

        orderClient.createOrder(order, accessToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка запрета на создание заказа, если пользователь не передал токен авторизации")
    public void createOrderWithoutAuthFail() {
        List<String> ingredients = List.of("60d3b41abdacab0026a733c6");
        Order order = new Order(ingredients);

        orderClient.createOrder(order, null)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверка возврата ошибки 400 при попытке отправить пустой список ингредиентов")
    public void createOrderWithoutIngredientsFail() {
        Order order = new Order(List.of());

        orderClient.createOrder(order, accessToken)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем")
    @Description("Проверка поведения системы (500 Internal Server Error) при передаче несуществующего хеша ингредиента")
    public void createOrderWithInvalidHashFail() {
        Order order = new Order(List.of("60d3b41abdacab0026a733c7"));

        orderClient.createOrder(order, accessToken)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}