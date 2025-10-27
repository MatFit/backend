package com.aftermath.backend.controller;

import com.aftermath.backend.service.AuthenticationService;
import com.aftermath.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import com.aftermath.backend.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public abstract class Controller {
    @Autowired
    protected UserService userService;
    @Autowired
    protected AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiResponseDTO handleBadRequestException(BadRequestException ex) {
        System.out.println(ex.getMessage());
        return new ApiResponseDTO(false, ex.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ApiResponseDTO handleAnyException(Exception e) {
        System.out.println("Error while processing exception");
        return new ApiResponseDTO(false, "somethingWrong");
    }
}
