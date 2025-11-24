package com.aftermath.backend.service;

import com.aftermath.backend.service.serviceInterface.TokenServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenService implements TokenServiceImpl {

    private final SecretKey secretKey;

    @Autowired
    public TokenService(CryptoService cryptoService) throws NoSuchAlgorithmException {
        this.secretKey = cryptoService.generateKey(256);
    }

    // Overload JWT token by searching the id of the User and passing it in as type String
    public String createJwtToken(Long id, Duration expireIn) {
        return createJwtToken(Long.toString(id), expireIn);
    }

    public String createJwtToken(String subject, Duration expireIn) {
        // Crazy how this does it in a few lines
        Long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + expireIn.toMillis());

        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Simple-JWT")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey.getEncoded()) // byte array as param not SecretKey object
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getEncoded())
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public UUID getUserIdFromToken(String token) {
        try {
            System.out.println("Parsing token to get claims");
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getEncoded())
                    .parseClaimsJws(token)
                    .getBody();


            // Use getSubject() for standard "sub" claim, not custom "subject"
            String userIdString = claims.getSubject();
            System.out.println("User ID string: " + userIdString);
            UUID uuid = UUID.fromString(userIdString);
            System.out.println("Converted to UUID: " + uuid);
            return uuid;
        } catch (Exception e) {
            System.out.println("Error in getUserIdFromToken: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private String hmacSha256(String data, SecretKey secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secret);
        byte[] hash = mac.doFinal(data.getBytes());
        return base64UrlEncode(hash);
    }
    private String base64UrlEncode(byte[] bytes) throws IllegalArgumentException {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}


//        Long nowMillis = System.currentTimeMillis(); // current time in miliseconds
//        Long expMillis = nowMillis + expireIn.toMillis();
//
//        // JWT Token structure : header + payload + signature
//        // Header
//        Map<String, Object> header = new HashMap<>();
//        header.put("alg", "HS256"); // HS256 is a symmetic algo to share one secret key to frontend app
//        header.put("typ", "JWT");
//
//        // Payload
//        Map<String, Object> payload = new HashMap<>();
//        payload.put("subject", subject);
//        payload.put("iss", "Simple-JWT"); // Change this to a legitamite token
//        payload.put("iat", nowMillis / 1000); // Expirations
//        payload.put("exp", expMillis / 1000);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String headerJSON, payloadJSON, encodedHeader, encodedPayload, data, signature;
//
//        // JSON conversion to header n payload
//        try {
//            headerJSON = objectMapper.writeValueAsString(header);
//            payloadJSON = objectMapper.writeValueAsString(payload);
//        } catch (JsonProcessingException e) {
//            System.out.println("Failed to convert objects into JSON strings: " + e);
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data format", e);
//        }
//        // Base64 encoding, encode header and payload, combine
//        try {
//            encodedHeader = base64UrlEncode(headerJSON.getBytes());
//            encodedPayload = base64UrlEncode(payloadJSON.getBytes());
//            data = encodedHeader + "." + encodedPayload;
//        } catch (IllegalArgumentException e) {
//            System.out.println("Failed in Base64 encoding (possible bad ASCII?): " + e);
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Encoding error", e);
//        }
//
//        // Create HMAC signature
//        try {
//            signature = hmacSha256(data, secretKey);
//        } catch (Exception e) {
//            System.out.println("Failed generating HMAC-SHA256 signature: " + e);
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Signature generation failed", e);
//        }
//
//
//        // Combine into token. (Header + Payload) + signature
//        return data + "." + signature;
