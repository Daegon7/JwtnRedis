package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.TokenResponse;
import com.example.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    public TokenResponse login(LoginRequest request) {
        // 실제로는 DB에서 사용자 인증을 해야 함 (여기선 생략)
        String username = request.getUsername();

        String accessToken = jwtTokenProvider.createAccessToken(username);
        String refreshToken = jwtTokenProvider.createRefreshToken();

        // Redis에 Refresh Token 저장 (key: username, value: refreshToken)
        redisTemplate.opsForValue().set("refresh:" + username, refreshToken, Duration.ofDays(7));

        return new TokenResponse(accessToken, refreshToken);
    }
}