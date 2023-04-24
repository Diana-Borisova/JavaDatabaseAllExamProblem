package com.softuni.jsonexercise.domain.dto.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductsSummaryDto {

    private String name;

    private Long productCount;

    private Double averagePrice;

    private BigDecimal totalRevenue;

}
