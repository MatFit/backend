package com.aftermath.backend.service;

import com.aftermath.backend.service.serviceInterface.TokenServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TokenService implements TokenServiceInterface {

    private final SecretKey secretKey;

    @Autowired
    public TokenService(CryptoService cryptoService) throws NoSuchAlgorithmException {
        this.secretKey = cryptoService.generateKey(256);
    }

    // Overload JWT token by searching the id of the User and passing it in as type String
    public String createJwtToken(Long id, Duration expireIn) {
        return createJwtToken(Long.toString(id), expireIn);
    }

    private String createJwtToken(String subject, Duration expireIn) {
        Long nowMillis = System.currentTimeMillis();
        Long expMillis = nowMillis + expireIn.toMillis();

        // JWT Token structure : header + payload + signature
        // Header
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256"); // HS256 is a symmetic algo to share one secret key to frontend app
        header.put("typ", "JWT");

        // Payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("subject", subject);
        payload.put("iss", "Simple-JWT"); // Change this to a legitamite token
        payload.put("iat", nowMillis / 1000); // Expirations
        payload.put("exp", expMillis / 1000);

        ObjectMapper objectMapper = new ObjectMapper();
        String headerJSON, payloadJSON, encodedHeader, encodedPayload, data, signature;

        try {
            headerJSON = objectMapper.writeValueAsString(header);
            payloadJSON = objectMapper.writeValueAsString(payload);
            encodedHeader = base64UrlEncode(headerJSON.getBytes());
            encodedPayload = base64UrlEncode(payloadJSON.getBytes());
            data = encodedHeader + "." + encodedPayload;
            signature = hmacSha256(data, secretKey);
        } catch (JsonProcessingException e) {
            // Log issue and throw bad http
            System.out.println("Failed to convert objects into strings for JSON: " + e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data format", e); // Will throw something better
        } catch (IllegalArgumentException e){
            System.out.println("Failed in base 64 encoding, perhapds bad ASCII? : " + e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data format", e); // Will throw something better
        }
        catch (Exception e){
            System.out.println("Failed in HMACSha256 signature: " + e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data format", e);
        }

        // Combine into token
        return data + "." + signature;
    }

    private String base64UrlEncode(byte[] bytes) throws IllegalArgumentException {
        for (byte b : bytes) {
            // Falls within ASCII bounds
            if (b < 0 || b > 127) {
                throw new IllegalArgumentException(
                        "Input contains non-ASCII bytes. Found byte value: " + (b & 0xFF));
            }
        }
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hmacSha256(String data, SecretKey secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secret);
        byte[] hash = mac.doFinal(data.getBytes());
        return base64UrlEncode(hash);
    }
}
