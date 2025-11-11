package com.example.auth.service;

import com.example.auth.dto.UserDto;
import com.example.auth.dto.UserInput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<UserDto> findUsers(UserInput where) {
        UserDto dto = new UserDto();
        dto.setId(where.getId());
        dto.setName(where.getName());
        dto.setEmail(where.getEmail());
        dto.setDescription(where.getDescription());
        return List.of(dto);
    }
}