package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.code.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import es.upm.miw.devops.code.UserActiveUpdate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testDeleteById() {
        webTestClient.delete()
                .uri("/user/1")
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteByIdNotFound() {
        webTestClient.delete()
                .uri("/user/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindById() {
        webTestClient.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    void testFindByIdNotFound() {
        webTestClient.get()
                .uri("/user/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateActive() {
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/2/active")
                        .queryParam("active", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("2")
                .jsonPath("$.active").isEqualTo(true);
    }


    @Test
    void testUpdateActiveNotFound() {
        webTestClient.put()
                .uri("/user/999/active?active=true")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testSearchBillable() {
        webTestClient.get()
                .uri("/user?billable=true")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1);
    }
    @Test
    void testSearchNonBillable() {
        webTestClient.get()
                .uri("/user?billable=false")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1);
    }
    @Test
    void testSearchWithMultipleConditions() {
        webTestClient.get()
                .uri("/user?firstName=Aa&billable=true")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1);
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

        webTestClient.put()
                .uri("/user/1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.firstName").isEqualTo("John")
                .jsonPath("$.familyName").isEqualTo("Smith")
                .jsonPath("$.email").isEqualTo("john.smith@gmail.com")
                .jsonPath("$.active").isEqualTo(false);

        // Restore seeded user
        User original = new User(
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
        );

        webTestClient.put()
                .uri("/user/1")
                .bodyValue(original)
                .exchange()
                .expectStatus().isOk();
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

        webTestClient.put()
                .uri("/user/999")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateActiveList() {
        List<UserActiveUpdate> updates = List.of(
                new UserActiveUpdate("1", true),
                new UserActiveUpdate("2", true)
        );

        webTestClient.patch()
                .uri("/user")
                .bodyValue(updates)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].active").isEqualTo(true)
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].active").isEqualTo(true);

        // Restore seeded state
        List<UserActiveUpdate> original = List.of(
                new UserActiveUpdate("1", true),
                new UserActiveUpdate("2", false)
        );

        webTestClient.patch()
                .uri("/user")
                .bodyValue(original)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testAdminCannotBeDeactivated() {
        List<UserActiveUpdate> updates = List.of(
                new UserActiveUpdate("1", false)
        );

        webTestClient.patch()
                .uri("/user")
                .bodyValue(updates)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].role").isEqualTo("ADMIN")
                .jsonPath("$[0].active").isEqualTo(true);
    }

}