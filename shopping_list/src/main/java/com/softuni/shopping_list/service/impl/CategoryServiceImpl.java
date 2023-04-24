package com.softuni.shopping_list.service.impl;

import com.softuni.shopping_list.model.entity.Category;
import com.softuni.shopping_list.model.entity.CategoryEnum;
import com.softuni.shopping_list.repository.CategoryRepository;
import com.softuni.shopping_list.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void initCategories() {
        if (this.categoryRepository.count() == 0){
        Arrays.stream(CategoryEnum.values()).
                forEach(categoryEnum -> {
                    Category category = new Category();
                    category.setName(categoryEnum);
                    this.categoryRepository.saveAndFlush(category);
                });
        }
    }

    @Override
    public Category findByName(CategoryEnum name) {
        return this.categoryRepository.findByName(name).orElse(null);
    }
}
