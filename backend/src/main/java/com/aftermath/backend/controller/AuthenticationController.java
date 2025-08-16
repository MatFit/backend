package com.aftermath.backend.controller;

import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.LoginResponse;

import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;

// Handles routing
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest req) throws IOException {
        System.out.println("HelloHelloHelloHelloHelloHelloHelloHelloHelloHello");
        userService.registerNewUser(req);
        return ResponseEntity.ok().build(); // We'll have them just log in instead of giving them a session rn
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) throws IOException {
        // Backend here handles /login where we require username and password
        // make sure that the password pass is hashed and compared to what's in the DB (where there the password is also hashed
        Boolean matches = userService.authenticateUser(req);

        if (matches){
            String sessionToken = generateSessionToken();
            ResponseCookie cookie = ResponseCookie.from("SESSION_TOKEN", sessionToken)
                    .httpOnly(true) // Only http
                    .secure(true) // Secure?
                    .path("/") // Idk
                    .maxAge(Duration.ofDays(2)) // Lifetime of cookie
                    .build();
            LoginResponse messageResponse = new LoginResponse("Login Success", null);
            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(messageResponse);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginResponse("Invalid credentials", null));
    }


    public String generateSessionToken(){
        return "session_" + System.currentTimeMillis() + "_" + Math.random();
    }

    public UserService getUserService() {
        return userService;
    }
}

