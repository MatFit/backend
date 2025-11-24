package com.aftermath.backend.service.serviceInterface;

import java.time.Duration;

public interface TokenServiceImpl {
    String createJwtToken(Long id, Duration expiresIn);
}
