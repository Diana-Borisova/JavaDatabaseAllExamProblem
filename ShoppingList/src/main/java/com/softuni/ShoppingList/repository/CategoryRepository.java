package com.softuni.ShoppingList.repository;

import com.softuni.ShoppingList.entity.Category;
import com.softuni.ShoppingList.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
