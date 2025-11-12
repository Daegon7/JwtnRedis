package com.example.auth.service;

import com.example.auth.dto.ApplianceDto;
import com.example.auth.dto.ApplianceInput;
import com.example.auth.entity.Appliance;
import com.example.auth.repository.ApplianceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplianceService {

    private final ApplianceRepository applianceRepository;

    public ApplianceService(ApplianceRepository applianceRepository) {
        this.applianceRepository = applianceRepository;
    }

    public List<ApplianceDto> findAppliance(ApplianceInput where) {

        // JPA로 전체 조회
        List<Appliance> appliances = applianceRepository.findAll();

        // Entity → DTO 변환
        return appliances.stream()
                .map(a -> {
                    ApplianceDto dto = new ApplianceDto();
                    dto.setId(a.getId());
                    dto.setName(a.getName());
                    dto.setBrand(a.getBrand());
                    dto.setPrice(a.getPrice());
                    dto.setDescription(a.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());


//        ApplianceDto fridge = new ApplianceDto();
//        fridge.setId("A1");
//        fridge.setName("Smart Refrigerator");
//        fridge.setBrand("Samsung");
//        fridge.setPrice(1200000);
//        fridge.setDescription("AI 냉장관리 기능 포함");
//
//        ApplianceDto washer = new ApplianceDto();
//        washer.setId("A2");
//        washer.setName("Washer X200");
//        washer.setBrand("LG");
//        washer.setPrice(890000);
//        washer.setDescription("AI 스팀 살균 세탁기");
//
//        return List.of(fridge, washer);
    }

    // ✅ QueryDSL 기반 동적 검색
    public List<ApplianceDto> searchAppliances(ApplianceInput where) {
        return applianceRepository.searchAppliances(where);
    }
}