package com.softuni.shopping_list.model.service;

import com.softuni.shopping_list.model.entity.Category;
import com.softuni.shopping_list.model.entity.CategoryEnum;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductServiceModel {
    private Long id;
    private String name;

    private String description;

    private BigDecimal price;

    private LocalDateTime neededBefore;

    private CategoryEnum category;
}
