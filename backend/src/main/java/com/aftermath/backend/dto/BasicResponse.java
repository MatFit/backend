package com.aftermath.backend.dto;
// Handle certain requests
public class BasicResponse {
    private String something;

    public BasicResponse(String something) {
        this.something = something;
    }
    // Getters and setters
    public String getSomething() { return something; }
}
