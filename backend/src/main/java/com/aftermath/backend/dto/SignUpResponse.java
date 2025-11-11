package com.aftermath.backend.dto;

import lombok.Getter;
import lombok.Setter;


public class SignUpResponse {
    private String JWTtoken;

    public SignUpResponse(String JWTtoken) {
        this.JWTtoken = JWTtoken;
    }
    public String getJWTtoken(){
        return JWTtoken;
    }
}
