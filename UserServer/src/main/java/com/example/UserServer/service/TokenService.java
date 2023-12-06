package com.example.UserServer.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class TokenService {

    private static final String KEY = "secretKey"; // Simple key for proof of concept

    public static String generateToken(String username) {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + 3600000)) // 1 hour expiry
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }
}