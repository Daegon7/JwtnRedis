package com.example.auth.dto;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String description;
}