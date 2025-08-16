package com.aftermath.backend.dto;

public class LoginResponse {
    private String message;
    private String sessionToken;

    public LoginResponse(String message, String sessionToken) {
        this.message = message;
        this.sessionToken = sessionToken;
    }

    public void setMessage(String message){
        this.message = message;
    }
    public void setSessionToken(String sessionToken){
        this.sessionToken = sessionToken;
    }

    public String getMessage(){
        return this.message;
    }
    public String getSessionToken(){
        return this.sessionToken;
    }
}