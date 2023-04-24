package com.softuni.ShoppingList.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;


    @Column(nullable = false)
    @Positive
    private BigDecimal price;

    @Column(nullable = false)
    @FutureOrPresent
    private LocalDateTime neededBefore;

    @Column(nullable = false, unique = true)
    private String username;

    @ManyToOne
    private Category category;


    public Product(String name, String description, BigDecimal price, LocalDateTime neededBefore, String username, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.neededBefore = neededBefore;
        this.username = username;
        this.category = category;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getNeededBefore() {
        return neededBefore;
    }

    public void setNeededBefore(LocalDateTime neededBefore) {
        this.neededBefore = neededBefore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
