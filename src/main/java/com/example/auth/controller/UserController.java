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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.*;

@Slf4j
@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/user")
public class UserController {

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
    @GetMapping("/graphql")
    public ResponseEntity<List<UserDto>> graphql() {

        // GraphQL 쿼리 정의
        String graphQLQuery = "{ users { id name email description } }";

        // 요청 객체 구성
        Map<String, String> body = Map.of("query", graphQLQuery);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // GraphQL 서버 주소
        String graphqlEndpoint = "http://localhost:9081/graphql";
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(graphqlEndpoint, request, JsonNode.class);

        log.debug(response.getBody().toPrettyString());

        /*
        public ResponseEntity<String> graphql() {
        // 전체 응답 JSON을 문자열로 반환
        String jsonString = response.getBody().toPrettyString();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString);
        */

        // JSON 파싱
        JsonNode itemsNode = response.getBody().path("data").path("users");

        // 배열에서 첫 번째 아이템 꺼내기
        if (itemsNode.isArray() && itemsNode.size() > 0) {
//            List<UserDto> users = new ArrayList<>();
//
//            for (JsonNode node : itemsNode) {
//                UserDto user = new UserDto();
//                user.setId(node.path("id").asText(null));
//                user.setName(node.path("name").asText(null));
//                user.setEmail(node.path("email").asText(null));
//                user.setDescription(node.path("description").asText(null));
//                users.add(user);
//            }

            ObjectMapper mapper = new ObjectMapper();
            List<UserDto> users = mapper.convertValue(
                    itemsNode,
                    new TypeReference<List<UserDto>>() {}
            );

            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

}