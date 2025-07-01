package com.example.auth.controller;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.TokenResponse;
import com.example.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization 헤더 없음");
        }

        String token = header.substring(7); // "Bearer " 제거

        try {
            authService.logout(token); // 내부에서 블랙리스트 등록
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("로그아웃 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 실패");
        }
    }



}