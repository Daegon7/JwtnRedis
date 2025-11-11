package com.example.auth.service;

import com.example.auth.dto.ApplianceDto;
import com.example.auth.dto.ApplianceInput;
import com.example.auth.dto.UserDto;
import com.example.auth.dto.UserInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplianceService {

    public List<ApplianceDto> findAppliance(ApplianceInput where) {
        ApplianceDto fridge = new ApplianceDto();
        fridge.setId("A1");
        fridge.setName("Smart Refrigerator");
        fridge.setBrand("Samsung");
        fridge.setPrice(1200000);
        fridge.setDescription("AI 냉장관리 기능 포함");

        ApplianceDto washer = new ApplianceDto();
        washer.setId("A2");
        washer.setName("Washer X200");
        washer.setBrand("LG");
        washer.setPrice(890000);
        washer.setDescription("AI 스팀 살균 세탁기");

        return List.of(fridge, washer);
    }
}