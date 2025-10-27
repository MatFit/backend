package com.aftermath.backend.service.serviceInterface;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public interface CryptoServiceInterface {
    SecretKey generateKey(int digits) throws NoSuchAlgorithmException;
}
