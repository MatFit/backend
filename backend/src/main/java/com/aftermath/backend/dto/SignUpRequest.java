package com.aftermath.backend.dto;


public class SignUpRequest {
    private String username;
    private String password;
    private String email;

    public SignUpRequest(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
}
