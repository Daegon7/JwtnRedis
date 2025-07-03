package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS")  // MSSQL에 만든 테이블명 대소문자 구분 없음
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(length = 8)
    private String id;

    @Column(length = 8)
    private String password;

    @Column(length = 20)
    private String name;

    @Column(length = 5)
    private String role;
}

