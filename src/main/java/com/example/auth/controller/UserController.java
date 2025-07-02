package com.example.auth.controller;

import com.example.auth.dto.UserDto;
import com.example.auth.dto.UserProfileResponse;
import com.example.auth.security.CustomUserDetails;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import org.springframework.http.*;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/user")
public class UserController {

    private final RestTemplate restTemplate = new RestTemplate();

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "프로필 조회", description = "현재 로그인한 사용자의 프로필을 반환합니다.")
    @GetMapping("/profile")
    public UserProfileResponse getProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return new UserProfileResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Operation(summary = "GRAPHQL 데이터 송수신", description = "GRAPHQL 데이터 송수신")
    @GetMapping("/graphql1")
    public ResponseEntity<String> graphql1() {

        String graphQLQuery = "{ hello  hello2 }";

        // GraphQL 요청 객체 생성
        Map<String, String> body = Map.of("query", graphQLQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // GraphQL 엔드포인트 호출
        String graphqlEndpoint = "http://localhost:8082/graphql";
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(graphqlEndpoint, request, JsonNode.class);

        String jsonString = response.getBody().toPrettyString();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString);
    }

    @Operation(summary = "GRAPHQL 데이터 송수신", description = "GRAPHQL 데이터 송수신")
    @GetMapping("/graphql2")
    public ResponseEntity<String> graphql2() {

        String graphQLQuery = "{ items { name description } }";

        // GraphQL 요청 객체 생성
        Map<String, String> body = Map.of("query", graphQLQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // GraphQL 엔드포인트 호출
        String graphqlEndpoint = "http://localhost:8082/graphql";
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(graphqlEndpoint, request, JsonNode.class);

        String jsonString = response.getBody().toPrettyString();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString);
    }

    @Operation(summary = "GRAPHQL 데이터 송수신", description = "GRAPHQL 데이터 송수신")
    @GetMapping("/graphql3")
    public ResponseEntity<UserDto> graphql3() {
        // GraphQL 쿼리 정의
        String graphQLQuery = "{ items { name description } }";


        // GraphQL 요청 객체 생성
        Map<String, String> body = Map.of("query", graphQLQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // GraphQL 엔드포인트 호출
        String graphqlEndpoint = "http://localhost:8082/graphql";
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(graphqlEndpoint, request, JsonNode.class);

        // JSON 파싱
        JsonNode itemsNode = response.getBody().path("data").path("items");

        System.out.println(response.getBody().toPrettyString());

        // 배열에서 첫 번째 아이템 꺼내기
        if (itemsNode.isArray() && itemsNode.size() > 0) {
            JsonNode firstItem = itemsNode.get(0);

            UserDto user = new UserDto();
            user.setName(firstItem.path("name").asText(null));
            user.setDescription(firstItem.path("description").asText(null));

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}