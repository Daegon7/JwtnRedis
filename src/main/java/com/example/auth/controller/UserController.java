package com.example.auth.controller;

import com.example.auth.dto.UserProfileResponse;
import com.example.auth.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Operation(summary = "프로필 조회", description = "현재 로그인한 사용자의 프로필을 반환합니다.")
    @GetMapping("/profile")
    public UserProfileResponse getProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return new UserProfileResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}