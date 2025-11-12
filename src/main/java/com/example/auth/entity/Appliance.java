package com.example.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appliance") // MySQL은 대소문자 구분하므로 소문자 사용 권장
@Getter
@Setter
@NoArgsConstructor
public class Appliance {
    @Id
    private String id;   // A1, A2 같은 문자열 ID

    @Column(length = 100, nullable = false)
    private String name; // 제품명

    @Column(length = 50, nullable = false)
    private String brand; // 브랜드명

    @Column(nullable = false)
    private int price;   // 가격 (정수)

    @Column(length = 255)
    private String description; // 설명
}
