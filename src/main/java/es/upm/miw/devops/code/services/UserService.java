package es.upm.miw.devops.code.services;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UsersDatabase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

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
}