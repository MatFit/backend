package com.aftermath.backend.service.serviceInterface;

import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.dto.SignUpResponse;
import com.aftermath.backend.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServiceImpl {
    SignUpResponse registerNewUser(SignUpRequest signUpRequest);
    User authenticateUser(LoginRequest loginRequest);
    List<User> getAllUsers();
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserById(UUID uuid);
    Optional<User> getUserByEmail(String email);
}
