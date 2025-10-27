package com.aftermath.backend.service.serviceInterface;

import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.LoginResponse;

import java.io.IOException;
import java.util.UUID;

public interface AuthenticationServiceInterface {
    LoginResponse login (LoginRequest dto) throws IOException;

    default String generateSessionToken() {
        return UUID.randomUUID().toString();
    }
}