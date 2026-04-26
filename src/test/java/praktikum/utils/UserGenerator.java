package praktikum.utils;

import praktikum.models.User;

import java.util.UUID;

public class UserGenerator {

    public static User randomUser() {
        String id = UUID.randomUUID().toString().substring(0, 8);

        return new User(
                "user_" + id + "@test.com",
                "pass1234_" + id,
                "name_" + id
        );
    }
}