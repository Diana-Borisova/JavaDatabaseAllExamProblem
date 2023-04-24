package com.softuni.jsonexercise.services;

import com.softuni.jsonexercise.domain.dto.products.ProductDto;
import com.softuni.jsonexercise.domain.dto.products.ProductNoBuyerDto;
import com.softuni.jsonexercise.domain.entities.Product;
import com.softuni.jsonexercise.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.softuni.jsonexercise.domain.constant.Paths.PRODUCTS_WITH_NO_BUYERS_PATH;
import static com.softuni.jsonexercise.domain.constant.Utils.MODEL_MAPPER;
import static com.softuni.jsonexercise.domain.constant.Utils.writeJsonIntoFile;

@Service
public class ProductServiceImpl implements ProductService {
   private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductNoBuyerDto> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException {

      final List<ProductNoBuyerDto> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDto.class))
                .map(ProductDto::toProductNoBuyerDto)
                .collect(Collectors.toList());
      writeJsonIntoFile(products, PRODUCTS_WITH_NO_BUYERS_PATH);
        return products;
    }
}
