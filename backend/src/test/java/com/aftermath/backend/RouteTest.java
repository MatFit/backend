package com.aftermath.backend;

import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.repository.UserRepository;
import com.aftermath.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RouteTest {
    @Autowired
    private MockMvc mockMvc; // Model view controller prototype w/o a full server running

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanDB(){
        userRepository.deleteAll();
    }

    @Test
    void testRegisterRoute() throws Exception {
        SignUpRequest req = new SignUpRequest("Username", "newpassword", "email@test.org");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // now check the repo has exactly one user
        assertThat(userRepository.count()).isEqualTo(1);

        SignUpRequest req2 = new SignUpRequest("Username1", "newpassword1", "email@test.org");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req2)))
                .andExpect(status().isOk());

        // two users
        assertThat(userRepository.count()).isEqualTo(2);
    }

    @Test
    void testLoginRoute() throws Exception {
        LoginRequest signUpRequest = new LoginRequest("Username", "newpassword");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        // now check the repo has exactly one user
        assertThat(userRepository.count()).isEqualTo(1);

        LoginRequest loginRequest = new LoginRequest("Username", "newpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
