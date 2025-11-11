package com.aftermath.backend.controller;
import java.util.Optional;

import com.aftermath.backend.dto.ApiResponseDTO;
import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.LoginResponse;

import com.aftermath.backend.model.User;
import com.aftermath.backend.service.AuthenticationService;
import com.aftermath.backend.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

// Handles routing
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController extends Controller {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, TokenService tokenService){
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    // Login Route generic
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(@Valid @RequestBody LoginRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Normal flow
        LoginResponse loginResponse = authenticationService.login(req);
        // Create cookie object n attach to response
        Cookie jwtCookie = new Cookie("JWT-TOKEN", loginResponse.getJWTtoken());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);

        response.addCookie(jwtCookie);

        return ApiResponseDTO.success(loginResponse).toResponseEntity();
    }

    @PostMapping(path = "/login_page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDTO<Optional<LoginResponse>>> checkExistingAuth(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        System.out.println("Controller method started");
        String existingToken = getJWTFromCookies(request);
        System.out.println("Token from cookies: " + existingToken);

        Optional<LoginResponse> loginResponse = authenticationService.validateExistingAuth(existingToken);
        System.out.println("After validateExistingAuth");

        return ApiResponseDTO.<Optional<LoginResponse>>success(loginResponse).toResponseEntity();
    }


    private String getJWTFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT-TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}

