package com.example.auth.controller;

import com.example.auth.dto.UserDto;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GraphQLUserController {

    @QueryMapping
    public List<UserDto> users() {
        List<UserDto> list = new ArrayList<>();

        UserDto u1 = new UserDto();
        u1.setId("1");
        u1.setName("daegon");
        u1.setEmail("daegon@example.com");
        u1.setDescription("첫 번째 사용자");

        UserDto u2 = new UserDto();
        u2.setId("2");
        u2.setName("neo");
        u2.setEmail("neo@example.com");
        u2.setDescription("두 번째 사용자");

        list.add(u1);
        list.add(u2);

        return list;
    }


}