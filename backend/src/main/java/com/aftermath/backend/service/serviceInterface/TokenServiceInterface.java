package com.aftermath.backend.service.serviceInterface;

import java.time.Duration;

public interface TokenServiceInterface {
    String createJwtToken(Long id, Duration expiresIn);
}
