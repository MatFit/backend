package com.aftermath.backend.service;
import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.model.User;
import com.aftermath.backend.repository.UserRepository;
import com.aftermath.backend.service.serviceInterface.UserServiceInterface;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Business Logic -> creating users, check cred
@Service
public class UserService implements UserServiceInterface {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder){
        this.repo = repo;
        this.encoder = encoder;
    }

    @NotNull
    public User registerNewUser(SignUpRequest signUpRequest){
        if (repo.existsByUsername(signUpRequest.getUsername())){
            throw new RuntimeException("Username already exists"); // Closes thread, so I believe the whole backend needs fixing later
        }

        String hashed = encoder.encode(signUpRequest.getPassword());
        User newUser = new User(null, signUpRequest.getUsername(), signUpRequest.getEmail(), hashed);
        repo.save(newUser);
        return newUser;
    }

    @NotNull
    public User authenticateUser(@NotNull LoginRequest loginRequest) {
        User foundUser = repo.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.getUsername()));
        if (!encoder.matches(loginRequest.getPassword(), foundUser.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return foundUser;
    }
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUserById(UUID uuid) {
        return repo.findById(uuid).orElse(null);
    }

    public User getUserByEmail(String email) {
        return repo.findByEmail(email).orElse(null); // assuming findByEmail returns Optional<User>
    }
}
