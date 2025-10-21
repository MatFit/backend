package com.aftermath.backend.service;
import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.model.User;
import com.aftermath.backend.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

// Business Logic -> creating users, check cred
@Service
public class UserService {
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
    public Boolean authenticateUser(@NotNull LoginRequest req){
        Optional<User> opt = repo.findByUsername(req.getUsername());

        // No users found
        if (opt.isEmpty()){ return false; }

        User foundUser = opt.get();

        return encoder.matches(req.getPassword(), foundUser.getPassword());
    }

    public UserRepository getRepo() {
        return repo;
    }
}
