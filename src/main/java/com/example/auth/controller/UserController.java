package com.example.auth.controller;

import com.example.auth.dto.UserDto;
import com.example.auth.dto.UserProfileResponse;
import com.example.auth.security.CustomUserDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.*;

@Slf4j
@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/user")
public class UserController{

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "프로필 조회", description = "/auth/login 에서 access token값 복사 붙이기 후 에 실행")
    @GetMapping("/profile")
    public UserProfileResponse getProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return new UserProfileResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

}