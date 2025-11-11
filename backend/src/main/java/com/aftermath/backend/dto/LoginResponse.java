package com.aftermath.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String jwtToken;
    private String userEmail;
    private String message;

    public LoginResponse(String sessionToken, String userEmail, String message) {
        this.jwtToken = jwtToken;
        this.userEmail = userEmail;
        this.message = message;
    }
    public LoginResponse(String sessionToken) {
        this.jwtToken = jwtToken;
    }

    public String getJWTtoken() {
        return jwtToken;
    }
}