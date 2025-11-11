package com.aftermath.backend.service;

import com.aftermath.backend.config.AppProperties;
import com.aftermath.backend.dto.LoginRequest;
import com.aftermath.backend.dto.LoginResponse;
import com.aftermath.backend.model.User;
import com.aftermath.backend.repository.UserRepository;
import com.aftermath.backend.service.serviceInterface.AuthenticationServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceInterface {
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenService tokenService;
    private final AppProperties appProperties;

    @Autowired
    public AuthenticationService(UserService userService, UserRepository userRepository, TokenService tokenService, AppProperties appProperties){
        this.userRepository = userRepository;
        this.userService = userService;
        this.tokenService = tokenService;
        this.appProperties = appProperties;
    }

    public LoginResponse login(LoginRequest loginRequest) throws IOException {
        return getAuthResponse(userService.authenticateUser(loginRequest));
    }
    public Optional<LoginResponse> validateExistingAuth(String token) {
        if (token == null) {
            return Optional.empty();
        }
        if (!tokenService.validateToken(token)) {
            return Optional.empty();
        }

        // Extract user ID from token
        UUID userId = tokenService.getUserIdFromToken(token);
        System.out.println("User ID from token: " + userId);

        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isEmpty()) {
            System.out.println("User not found for ID: " + userId);
            return Optional.empty();
        }

        // Construct and return login response
        User user = userOptional.get();
        System.out.println("User found: " + user);
        System.out.println("User email: " + user.getEmail());
        System.out.println("User username: " + user.getUsername());
        System.out.println("Token: " + token);

        LoginResponse loginResponse = new LoginResponse(
                token,
                user.getEmail(),
                "Already authenticated"
        );
        System.out.println("I believe were good");
        return Optional.of(loginResponse);
    }
    public String createJWTToken(User user) {
        String temp = tokenService.createJwtToken(user.getId().toString(), Duration.of(appProperties.getAuth().getAccessTokenExpirationMsec(), ChronoUnit.MILLIS));
        System.out.println(temp);
        return temp;
    }
    private LoginResponse getAuthResponse(User user) {
        String accessToken = createJWTToken(user);
        return new LoginResponse(accessToken);
    }
}
