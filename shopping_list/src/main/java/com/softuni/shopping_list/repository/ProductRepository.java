package com.softuni.shopping_list.repository;
import com.softuni.shopping_list.model.entity.CategoryEnum;
import com.softuni.shopping_list.model.entity.Product;
import com.softuni.shopping_list.model.service.ProductServiceModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT sum(p.price) from Product p ")
    BigDecimal findProductTotalSum();

    Optional<Product> findByName(String name);

    List<Product> findAllByCategory_Name(CategoryEnum categoryEnum);
}
