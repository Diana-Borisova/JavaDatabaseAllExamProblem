package com.softuni.ShoppingList.repository;

import com.softuni.ShoppingList.entity.Product;
import com.softuni.ShoppingList.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
