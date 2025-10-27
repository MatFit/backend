package com.aftermath.backend.service.serviceInterface;

import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.model.User;

import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {
    User registerNewUser(SignUpRequest signUpRequest);
    User authenticateUser(LoginRequest loginRequest);
    List<User> getAllUsers();
    User getUserById(UUID uuid);
    User getUserByEmail(String email);
}
