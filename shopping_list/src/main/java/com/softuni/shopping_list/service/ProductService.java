package com.softuni.shopping_list.service;

import com.softuni.shopping_list.model.entity.CategoryEnum;
import com.softuni.shopping_list.model.service.ProductServiceModel;
import com.softuni.shopping_list.model.views.ProductViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductServiceModel findByName(String name);

    void addProduct(ProductServiceModel productServiceModel);

    BigDecimal getTotalSum();

    List<ProductViewModel> findAllByCategoryName(CategoryEnum categoryEnum);

    void buyById(Long id);

    void buyAllProducts();

}
