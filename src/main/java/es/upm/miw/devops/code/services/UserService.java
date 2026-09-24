package es.upm.miw.devops.code.services;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UserActiveUpdate;
import es.upm.miw.devops.code.UsersDatabase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UsersDatabase usersDatabase;

    public UserService(UsersDatabase usersDatabase) {
        this.usersDatabase = usersDatabase;
    }

    // Returns a user if the id exists
    public Optional<User> findById(String id) {
        return usersDatabase.findById(id);
    }

    public List<User> search(String firstName, String email, Boolean billable) {
        return usersDatabase.findAll().stream()
                .filter(user -> firstName == null || user.getFirstName().equalsIgnoreCase(firstName))
                .filter(user -> email == null || user.getEmail().equalsIgnoreCase(email))
                .filter(user -> billable == null || user.isBillable() == billable)
                .toList();
    }

    public boolean deleteById(String id) {
        return usersDatabase.deleteById(id);
    }

    public Optional<User> update(String id, User user) {
        return usersDatabase.update(id, user);
    }

    public Optional<User> updateActive(String id, boolean active) {
        return usersDatabase.updateActive(id, active);
    }

    public List<User> updateActive(List<UserActiveUpdate> updates) {
        return updates.stream()
                .map(update -> usersDatabase.updateActive(update.id(), update.active()))
                .flatMap(Optional::stream)
                .toList();
    }
}