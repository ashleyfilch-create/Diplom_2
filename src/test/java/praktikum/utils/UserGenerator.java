package praktikum.utils;

import praktikum.models.User;
import java.util.UUID;

public class UserGenerator {

    // Генерация полностью случайного валидного пользователя
    public static User randomUser() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                "user_" + id + "@test.com",
                "pass1234_" + id,
                "name_" + id
        );
    }

    // Пользователь без email (обязательное поле согласно документации)
    public static User userWithoutEmail() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        return new User(null, "pass1234_" + id, "name_" + id);
    }

    // Пользователь без пароля
    public static User userWithoutPassword() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        return new User("user_" + id + "@test.com", null, "name_" + id);
    }

    // Пользователь без имени
    public static User userWithoutName() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        return new User("user_" + id + "@test.com", "pass1234_" + id, null);
    }
}