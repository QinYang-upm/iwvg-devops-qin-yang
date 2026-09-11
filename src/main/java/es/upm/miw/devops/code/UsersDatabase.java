package es.upm.miw.devops.code;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersDatabase {

    private final List<User> users = new ArrayList<>();

    public UsersDatabase() {
        // Initial data (seeder)
        users.add(new User("1", "Aa", "Bb", "aa@gmail.com"));
        users.add(new User("2", "Xx", "Yy", "xx@gmail.com"));
    }

    public Optional<User> findById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}