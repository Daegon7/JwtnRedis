package com.example.auth.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "에러 응답 형식")
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "USER_NOT_FOUND")
    private final String code;

    @Schema(description = "에러 메시지", example = "사용자를 찾을 수 없습니다.")
    private final String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}