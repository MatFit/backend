package com.aftermath.backend.service;

import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.LoginResponse;
import com.aftermath.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthService(UserService userService, UserRepository userRepository){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public LoginResponse login(LoginRequest req) throws IOException {
        boolean matches = userService.authenticateUser(req);

        if (matches) {
            String sessionToken = generateSessionToken();
            ResponseCookie cookie = ResponseCookie.from("SESSION_TOKEN", sessionToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofDays(2))
                    .build();

            return new LoginResponse("Login Success", generateSessionToken());
        }

        return new LoginResponse("Invalid credentials", null);
    }

    private String generateSessionToken() {
        return UUID.randomUUID().toString(); // simple placeholder; replace with JWT or secure token gen
    }
}
