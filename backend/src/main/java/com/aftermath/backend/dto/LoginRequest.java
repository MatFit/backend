package com.aftermath.backend.dto;

// Handle certain requests
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
