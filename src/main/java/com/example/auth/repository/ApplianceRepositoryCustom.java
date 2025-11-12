package com.example.auth.repository;

import com.example.auth.dto.ApplianceDto;
import com.example.auth.dto.ApplianceInput;

import java.util.List;

public interface ApplianceRepositoryCustom {
    List<ApplianceDto> searchAppliances(ApplianceInput where);
}