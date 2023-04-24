package com.softuni.shopping_list.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    private String name;

    private String description;

    private BigDecimal price;

    private LocalDateTime neededBefore;

    @ManyToOne
    private Category category;


    public Product() {
    }

    public Product(String name, String description, BigDecimal price, LocalDateTime neededBefore, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.neededBefore = neededBefore;
        this.category = category;

    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(name = "price",nullable = false)
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Column(name = "needed_before", nullable = false)
    @FutureOrPresent
    public LocalDateTime getNeededBefore() {
        return neededBefore;
    }

    public Product setNeededBefore(LocalDateTime neededBefore) {
        this.neededBefore = neededBefore;
        return this;
    }



    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

}
