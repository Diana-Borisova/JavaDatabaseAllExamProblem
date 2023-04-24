package com.softuni.jsonexercise.services;

import com.softuni.jsonexercise.domain.dto.products.ProductNoBuyerDto;
import com.softuni.jsonexercise.domain.entities.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductNoBuyerDto> findAllByPriceBetweenAndBuyerIsNullOrderByPrice (BigDecimal low, BigDecimal high) throws IOException;

}
