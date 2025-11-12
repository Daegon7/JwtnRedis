package com.example.auth.controller;

import com.example.auth.dto.ApplianceDto;
import com.example.auth.dto.ApplianceInput;
import com.example.auth.service.ApplianceService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphqlApplianceController {

    private final ApplianceService applianceService;

    public GraphqlApplianceController(ApplianceService applianceService) {
        this.applianceService = applianceService;
    }

    @QueryMapping(name = "appliances")
    public List<ApplianceDto> appliances(@Argument ApplianceInput where) {
        // 단순히 서비스 호출만
        //return applianceService.findAppliance(where);

        //return applianceService.searchAppliances(where);

        return applianceService.findAppliancesByMyBatis(where); // ✅ MyBatis 메소드로 변경
    }
}

