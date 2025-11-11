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

    private final RestTemplate restTemplate = new RestTemplate();

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

    @Operation(
            summary = "GRAPHQL → JSON 문자열 응답",
            description = "UserDto 쿼리를 수행하고 순수 JSON 문자열로 반환",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"query\": \"query { users(where: { id: \\\"test1\\\", name: \\\"test2\\\", email: \\\"test3\\\", description: \\\"description4\\\" }) { id name email description } }\"\n" +
                                            "}"
                            )
                    )
            )
    )
    @PostMapping("/graphql")
    public ResponseEntity<JsonNode> graphql(HttpServletRequest request) {

        // 요청 Body 읽기
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim()).append(" ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // JSON 파싱
        JSONObject obj = new JSONObject(sb.toString());
        String query = obj.getString("query");

        // GraphQL 쿼리 문자열 정리
        String graphQLQuery = query.replaceAll("\\s+", " ").trim();

        // 요청 객체 구성
        Map<String, String> body = Map.of("query", graphQLQuery);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // GraphQL 서버 주소
        String graphqlEndpoint = "http://localhost:8081/graphql";
        ResponseEntity<JsonNode> response =
                restTemplate.postForEntity(graphqlEndpoint, entity, JsonNode.class);

        // 테스트
        log.debug(response.getBody().toPrettyString());

        // ✅ 응답을 그대로 반환 (프록시 역할)
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

}