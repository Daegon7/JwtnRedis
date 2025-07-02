package com.example.auth.controller;

import com.example.auth.dto.ApplianceDto;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.ArrayList;

@Controller
public class ApplianceController {

    @QueryMapping
    public List<ApplianceDto> appliances() {
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

