package com.aftermath.backend.dto;

public class SignUpResponse {
    private String sessionToken;

    public SignUpResponse(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
