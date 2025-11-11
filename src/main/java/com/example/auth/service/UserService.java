package com.example.auth.service;

import com.example.auth.dto.UserDto;
import com.example.auth.dto.UserInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public List<UserDto> findUsers(UserInput where) {
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

        // where 조건에 따라 필터링
//        if (where.getId() != null) {
//            return list.stream()
//                    .filter(u -> u.getId().equals(where.getId()))
//                    .toList();
//        }

        return list;
    }
}