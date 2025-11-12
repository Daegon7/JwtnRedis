package com.example.auth.repository;

import com.example.auth.entity.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplianceRepository extends JpaRepository<Appliance, String>, ApplianceRepositoryCustom {
    // 기본 CRUD + Custom QueryDSL 메소드 사용 가능
}
