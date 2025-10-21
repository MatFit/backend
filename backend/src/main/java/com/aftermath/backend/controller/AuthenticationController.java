package com.aftermath.backend.controller;

import com.aftermath.backend.dto.ApiResponseDTO;
import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.LoginResponse;

import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.service.AuthService;
import com.aftermath.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;

// Handles routing
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController extends Controller {
    private final AuthService authService;

    @Autowired
    public AuthenticationController(AuthService authService){
        this.authService = authService;
    }

    // Abstract away login like this into authservice
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(@Valid @RequestBody LoginRequest req) throws IOException {
        LoginResponse loginResponse = authService.login(req);
        return ApiResponseDTO.success(loginResponse).toResponseEntity();
    }
}

