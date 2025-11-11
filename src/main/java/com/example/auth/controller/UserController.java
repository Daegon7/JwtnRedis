package com.example.auth.controller;

import com.example.auth.dto.UserDto;
import com.example.auth.dto.UserProfileResponse;
import com.example.auth.security.CustomUserDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "GRAPHQL → JSON 문자열 응답", description = "UserDto 쿼리를 수행하고 순수 JSON 문자열로 반환")
    @PostMapping("/graphql")
    public ResponseEntity<Map<String, Object>> graphql(HttpServletRequest request1) {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request1.getReader()) {
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

        // GraphQL 쿼리 정의
        //"query { users(where: { id: \"test1\", name: \"test2\", email: \"test3\", description: \"description4\" }) { id name email description } }";
        String graphQLQuery = query
                .replaceAll("\\s+", " ")   // 모든 공백/개행을 하나의 공백으로
                .trim();                   // 앞뒤 공백 제거

        // 요청 객체 구성
        Map<String, String> body = Map.of("query", graphQLQuery);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // GraphQL 서버 주소
        String graphqlEndpoint = "http://localhost:8081/graphql";
        ResponseEntity<JsonNode> response =
                restTemplate.postForEntity(graphqlEndpoint, request, JsonNode.class);

        log.debug(response.getBody().toPrettyString());

        // 응답 파싱
        JsonNode usersNode = response.getBody().get("data").get("users");

        ObjectMapper mapper = new ObjectMapper();
        List<UserDto> users = mapper.convertValue(
                usersNode,
                new TypeReference<List<UserDto>>() {}
        );

        // ✅ GraphQL 표준 응답 구조로 감싸기
        Map<String, Object> data = Map.of("users", users);
        Map<String, Object> result = Map.of("data", data);

        return ResponseEntity.ok(result);

    }

}