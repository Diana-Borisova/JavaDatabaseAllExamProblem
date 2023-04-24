package com.softuni.shopping_list.service.impl;

import com.softuni.shopping_list.model.entity.CategoryEnum;
import com.softuni.shopping_list.model.entity.Product;
import com.softuni.shopping_list.model.service.ProductServiceModel;
import com.softuni.shopping_list.model.views.ProductViewModel;
import com.softuni.shopping_list.repository.ProductRepository;
import com.softuni.shopping_list.service.CategoryService;
import com.softuni.shopping_list.service.ProductService;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public ProductServiceModel findByName(String name) {
        return this.productRepository
                .findByName(name)
                .map(product -> modelMapper.map(product, ProductServiceModel.class))
                .orElse(null);
    }

    @Override
    public void addProduct(ProductServiceModel productServiceModel) {
        Product product = modelMapper.map(productServiceModel, Product.class);
        product.setCategory(categoryService.findByName(productServiceModel.getCategory()));
       /* product.setName(productServiceModel.getName());
        product.setDescription(product.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setNeededBefore(productServiceModel.getNeededBefore());*/
        this.productRepository.save(product);
    }

    @Override
    public BigDecimal getTotalSum() {
        return this.productRepository.findProductTotalSum();
    }

    @Override
    public List<ProductViewModel> findAllByCategoryName(CategoryEnum categoryEnum) {
        return this.productRepository.findAllByCategory_Name(categoryEnum)
                .stream()
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void buyById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void buyAllProducts() {
        productRepository.deleteAll();
    }
}
