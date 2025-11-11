package com.example.auth.controller;

import com.example.auth.dto.UserDto;
import com.example.auth.dto.UserInput;
import com.example.auth.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GraphQLController {

    private final UserService userService;

    public GraphQLController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public List<UserDto> users(@Argument UserInput where) {
        // 단순히 서비스 호출만
        return userService.findUsers(where);
    }


}