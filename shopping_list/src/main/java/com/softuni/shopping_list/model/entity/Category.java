package com.softuni.shopping_list.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    @Column(name = "name", nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryEnum name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Category() {
    }

    public Category(CategoryEnum name, String description) {
        this.name = name;
        this.description = description;
    }


    public CategoryEnum getName() {
        return name;
    }

    public Category setName(CategoryEnum name) {
        this.name = name;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }
}
