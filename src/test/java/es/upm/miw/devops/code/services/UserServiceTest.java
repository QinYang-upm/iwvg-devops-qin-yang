package es.upm.miw.devops.code.services;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UsersDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        UsersDatabase usersDatabase = new UsersDatabase();
        userService = new UserService(usersDatabase);
    }

    @Test
    void testDeleteById() {
        boolean deleted = userService.deleteById("1");

        assertThat(deleted).isTrue();
        assertThat(userService.findById("1")).isEmpty();
    }

    @Test
    void testDeleteByIdNotFound() {
        boolean deleted = userService.deleteById("999");

        assertThat(deleted).isFalse();
    }

    @Test
    void testFindById() {
        assertThat(userService.findById("1"))
                .isPresent()
                .get()
                .extracting(User::getId)
                .isEqualTo("1");
    }

    @Test
    void testFindByIdNotFound() {
        assertThat(userService.findById("999")).isEmpty();
    }

    @Test
    void testUpdateActive() {
        User user = userService.updateActive("1", false)
                .orElseThrow();

        assertThat(user.isActive()).isFalse();
    }

    @Test
    void testUpdateActiveNotFound() {
        assertThat(userService.updateActive("999", true))
                .isEmpty();
    }

    @Test
    void testSearchBillableUsers() {
        List<User> users = userService.search(null, null, true);

        assertThat(users)
                .hasSize(1)
                .extracting(User::getId)
                .contains("1");
    }

    @Test
    void testSearchNonBillableUsers() {
        List<User> users = userService.search(null, null, false);

        assertThat(users)
                .hasSize(1)
                .extracting(User::getId)
                .contains("2");
    }

    @Test
    void testSearchByEmailAndBillable() {
        List<User> users =
                userService.search("Aa", "aa@gmail.com", true);

        assertThat(users)
                .hasSize(1);
    }

    @Test
    void testUpdate() {
        User request = new User(
                "999",
                "John",
                "Smith",
                "john.smith@gmail.com",
                "87654321B",
                "Street 10",
                "Sevilla",
                "Sevilla",
                "41001",
                false
        );

        User updated = userService.update("1", request).orElseThrow();

        assertThat(updated.getId()).isEqualTo("1");
        assertThat(updated.getFirstName()).isEqualTo("John");
        assertThat(updated.getFamilyName()).isEqualTo("Smith");
        assertThat(updated.getEmail()).isEqualTo("john.smith@gmail.com");
        assertThat(updated.getIdentity()).isEqualTo("87654321B");
        assertThat(updated.getAddress()).isEqualTo("Street 10");
        assertThat(updated.getCity()).isEqualTo("Sevilla");
        assertThat(updated.getProvince()).isEqualTo("Sevilla");
        assertThat(updated.getPostalCode()).isEqualTo("41001");
        assertThat(updated.isActive()).isFalse();
    }

    @Test
    void testUpdateNotFound() {
        User request = new User(
                "999",
                "John",
                "Smith",
                "john.smith@gmail.com",
                "87654321B",
                "Street 10",
                "Sevilla",
                "Sevilla",
                "41001",
                false
        );

        assertThat(userService.update("999", request)).isEmpty();
    }
}
