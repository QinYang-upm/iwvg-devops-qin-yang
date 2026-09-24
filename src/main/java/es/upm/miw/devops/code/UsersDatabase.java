package es.upm.miw.devops.code;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersDatabase {

    private final List<User> users = new ArrayList<>();

    public UsersDatabase() {
        users.add(new User(
                "1",
                "Aa",
                "Bb",
                "aa@gmail.com",
                "12345678A",
                "Street 1",
                "Madrid",
                "Madrid",
                "28001",
                true
        ));

        users.add(new User(
                "2",
                "Xx",
                "Yy",
                "xx@gmail.com",
                "",
                "Street 2",
                "Madrid",
                "Madrid",
                "28002",
                false
        ));
    }

    public Optional<User> findById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public boolean deleteById(String id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public Optional<User> updateActive(String id, boolean active) {
        return findById(id)
                .map(user -> {
                    user.setActive(active);
                    return user;
                });
    }

    public Optional<User> update(String id, User user) {
        return findById(id)
                .map(existingUser -> {
                    existingUser.updateFrom(user);
                    return existingUser;
                });
    }
}
