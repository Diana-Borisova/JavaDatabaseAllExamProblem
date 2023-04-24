package com.softuni.shopping_list.model.views;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductViewModel {

    private Long id;

    private String name;

    private BigDecimal price;
}
