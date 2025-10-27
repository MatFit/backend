package com.aftermath.backend.service;

import com.aftermath.backend.service.serviceInterface.CryptoServiceInterface;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class CryptoService implements CryptoServiceInterface {
    public SecretKey generateKey(int digits) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(digits);
        return keyGen.generateKey();
    }
}
