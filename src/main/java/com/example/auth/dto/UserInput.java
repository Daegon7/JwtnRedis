package com.example.auth.dto;

import lombok.Data;

@Data
public class UserInput {
    private String id;
    private String name;
    private String email;
    private String description;
}
