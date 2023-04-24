package com.softuni.shopping_list.repository;

import com.softuni.shopping_list.model.entity.Category;
import com.softuni.shopping_list.model.entity.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(CategoryEnum name);
}
