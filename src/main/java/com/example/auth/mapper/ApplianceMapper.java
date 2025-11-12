package com.example.auth.mapper;

import com.example.auth.dto.ApplianceDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplianceMapper {

    //@Select("SELECT * FROM appliance")
    List<ApplianceDto> findAll();

    //@Select("SELECT * FROM appliance WHERE id = #{id}")
    ApplianceDto findById(String id);
}