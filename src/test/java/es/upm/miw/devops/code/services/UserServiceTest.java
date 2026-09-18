package es.upm.miw.devops.code.services;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UsersDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}