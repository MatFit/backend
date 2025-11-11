package com.aftermath.backend.service;
import com.aftermath.backend.config.AppProperties;
import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.LoginResponse;
import com.aftermath.backend.dto.SignUpRequest;
import com.aftermath.backend.dto.SignUpResponse;
import com.aftermath.backend.model.User;
import com.aftermath.backend.repository.UserRepository;
import com.aftermath.backend.service.serviceInterface.UserServiceInterface;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sound.midi.SysexMessage;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final AppProperties appProperties;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder, TokenService tokenService, AppProperties appProperties){
        this.repo = repo;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.appProperties = appProperties;
    }

    @NotNull
    public SignUpResponse registerNewUser(SignUpRequest signUpRequest){
        if (getUserByUsername(signUpRequest.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists"); // Closes thread, so I believe the whole backend needs fixing later
        }
        if (getUserByEmail(signUpRequest.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }


        String hashed = encoder.encode(signUpRequest.getPassword());
        User newUser = new User(null, signUpRequest.getUsername(), signUpRequest.getEmail(), hashed);
        repo.save(newUser);
        return getAuthResponse(newUser);
    }
    @NotNull
    public User authenticateUser(@NotNull LoginRequest loginRequest) {
        Optional<User> userOptional = repo.findByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + loginRequest.getUsername());
        }
        User foundUser = userOptional.get();

        if (!encoder.matches(loginRequest.getPassword(), foundUser.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return foundUser;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }
    public Optional<User> getUserByUsername(String username){
        return repo.findByUsername(username);
    }

    public Optional<User> getUserById(UUID uuid) {
        return repo.findById(uuid);
    }

    public Optional<User> getUserByEmail(String email) {
        return repo.findByEmail(email);
    }
    public String createJWTToken(User user) {
        return tokenService.createJwtToken(user.getId().toString(), Duration.of(appProperties.getAuth().getAccessTokenExpirationMsec(), ChronoUnit.MILLIS));
    }
    private SignUpResponse getAuthResponse(User user) {
        String accessToken = createJWTToken(user);
        return new SignUpResponse(accessToken);
    }
}
