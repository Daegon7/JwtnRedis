package com.example.auth.repository;

import com.example.auth.entity.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplianceRepository extends JpaRepository<Appliance, String> {

    // 기본 제공되는 기능
    // findAll(), findById(), save(), deleteById() 등 자동으로 사용 가능

}

