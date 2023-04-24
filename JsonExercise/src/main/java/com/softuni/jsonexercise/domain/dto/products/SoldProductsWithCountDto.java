package com.softuni.jsonexercise.domain.dto.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SoldProductsWithCountDto {
    private Integer count;
    private List<ProductBasicInfo> products;

    public SoldProductsWithCountDto(List<ProductBasicInfo> products) {
        this.products = products;
        this.count = products.size();
    }
}
