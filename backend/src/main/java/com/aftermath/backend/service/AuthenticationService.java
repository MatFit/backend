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
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

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
    public String createAccessToken(User user) {
        return tokenService.createJwtToken(user.getId().toString(), Duration.of(appProperties.getAuth().getAccessTokenExpirationMsec(), ChronoUnit.MILLIS));
    }
    private LoginResponse getAuthResponse(User user) {
        String accessToken = createAccessToken(user);
        return new LoginResponse(accessToken);
    }
}
