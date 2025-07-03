package com.example.auth.repository;

import com.example.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    // 기본 제공되는 기능
    // findAll(), findById(), save(), deleteById() 등 자동으로 사용 가능

    // 사용자 지정 쿼리 메서드 예시
    List<User> findByRole(String role);
    List<User> findByNameContaining(String name);
}

