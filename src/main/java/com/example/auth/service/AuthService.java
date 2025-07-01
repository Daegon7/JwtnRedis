package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.TokenResponse;
import com.example.auth.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public void logout(String accessToken) {
        try {
            String username = jwtTokenProvider.getUsername(accessToken);
            String jti = jwtTokenProvider.extractJti(accessToken);
            Duration ttl = jwtTokenProvider.getRemainingTTL(accessToken);

            redisTemplate.delete("refresh:" + username);

            if (ttl != null && !ttl.isNegative() && !ttl.isZero()) {
                redisTemplate.opsForValue().set("blacklist:" + jti, "true", ttl);
            } else {
                redisTemplate.opsForValue().set("blacklist:" + jti, "true", Duration.ofHours(1));
            }

        } catch (Exception e) {
            log.warn("로그아웃 중 예외 발생: {}", e.getMessage());
            // 예외를 전파하지 않고 무시하거나, 필요 시 커스텀 예외로 감싸서 throw
        }
    }



}