package com.example.auth.repository;

import com.example.auth.dto.ApplianceDto;
import com.example.auth.dto.ApplianceInput;
import com.example.auth.entity.Appliance;
import com.example.auth.entity.QAppliance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ApplianceRepositoryImpl implements ApplianceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ApplianceRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ApplianceDto> searchAppliances(ApplianceInput where) {
        QAppliance appliance = QAppliance.appliance;

        var query = queryFactory.selectFrom(appliance);

        if (where.getBrand() != null && !where.getBrand().isEmpty()) {
            query.where(appliance.brand.eq(where.getBrand()));
        }
        if (where.getName() != null && !where.getName().isEmpty()) {
            query.where(appliance.name.containsIgnoreCase(where.getName()));
        }
//        if (where.getMaxPrice() != null && where.getMaxPrice() > 0) {
//            query.where(appliance.price.loe(where.getMaxPrice()));
//        }

        List<Appliance> result = query.fetch();

        return result.stream()
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
    }
}
