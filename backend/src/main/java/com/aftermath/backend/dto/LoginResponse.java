package com.aftermath.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String sessionToken;

    public LoginResponse(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}