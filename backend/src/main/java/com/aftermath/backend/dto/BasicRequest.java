package com.aftermath.backend.dto;
// Handle certain requests
public class BasicRequest {
    private String something;

    public BasicRequest(String something) {
        this.something = something;
    }
    // Getters and setters
    public String getSomething() { return something; }
}
