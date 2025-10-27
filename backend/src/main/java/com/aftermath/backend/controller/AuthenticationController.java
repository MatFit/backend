package com.aftermath.backend.controller;

import com.aftermath.backend.dto.ApiResponseDTO;
import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.LoginResponse;

import com.aftermath.backend.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

// Handles routing
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController extends Controller {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    // Abstract away login like this into authservice
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(@Valid @RequestBody LoginRequest req) throws IOException {
        LoginResponse loginResponse = authenticationService.login(req);
        return ApiResponseDTO.success(loginResponse).toResponseEntity();
    }
}

