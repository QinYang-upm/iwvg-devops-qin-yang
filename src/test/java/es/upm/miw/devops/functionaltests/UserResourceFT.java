package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
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
}