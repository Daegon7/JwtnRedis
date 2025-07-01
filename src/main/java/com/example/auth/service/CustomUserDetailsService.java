package com.example.auth.service;

import com.example.auth.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        /*
        if (user == null) {
            throw new UserNotFoundException();
        }

        User user = userRepository.findByUsername(username)
        .orElseThrow(UserNotFoundException::new); // 🔥 여기서 사용

        * */
        // 실제로는 DB에서 사용자 조회
        return new CustomUserDetails(username, "daegon@example.com", "USER", "dummyPassword");
    }
}