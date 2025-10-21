package com.aftermath.backend.controller;

import com.aftermath.backend.dto.ApiResponseDTO;
import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.model.User;
import com.aftermath.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(path = "/create")
    public ResponseEntity<ApiResponseDTO<User>> inviteUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user = userService.registerNewUser(signUpRequest);
        return ApiResponseDTO.success(user).toResponseEntity();
    }
}
