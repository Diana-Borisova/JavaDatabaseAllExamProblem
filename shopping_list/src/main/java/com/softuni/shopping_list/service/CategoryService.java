package com.softuni.shopping_list.service;

import com.softuni.shopping_list.model.entity.Category;
import com.softuni.shopping_list.model.entity.CategoryEnum;
import com.softuni.shopping_list.repository.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CategoryService  {
    void initCategories();
    Category findByName(CategoryEnum name);

}
