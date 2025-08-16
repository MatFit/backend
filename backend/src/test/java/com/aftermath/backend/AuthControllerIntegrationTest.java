package com.aftermath.backend;

import com.aftermath.backend.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// Tests here involving standard HTTP req something that would be seen when
// Front end communicates with back
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest {
    @LocalServerPort int port;
    @Autowired TestRestTemplate rest;

    @Test
    void signupIntegration() {
        SignUpRequest req = new SignUpRequest("user123","user@example.com","P@ssw0rd!");
        URI url = URI.create("http://localhost:" + port + "/api/auth/signup");
        ResponseEntity<Void> resp = rest.postForEntity(url, req, Void.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}