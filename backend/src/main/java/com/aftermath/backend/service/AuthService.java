package com.aftermath.backend.service;

import com.aftermath.backend.model.User;
import com.aftermath.backend.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthService(UserService userService, UserRepository userRepository){
        this.userRepository = userRepository;
        this.userService = userService;
    }
}
