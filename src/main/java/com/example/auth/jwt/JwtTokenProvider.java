package com.example.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.time.Duration;


@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long accessTokenValidity = 1000 * 60 * 30; // 30분
    private final long refreshTokenValidity = 1000 * 60 * 60 * 24 * 7; // 7일

    public String createAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractJti(String token) {
        return parseClaims(token).getId(); // JTI 추출
    }

    public Duration getRemainingTTL(String token) {
        Date expiration = parseClaims(token).getExpiration();
        long now = System.currentTimeMillis();
        return Duration.ofMillis(expiration.getTime() - now);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateJti() {
        return java.util.UUID.randomUUID().toString();
    }


}