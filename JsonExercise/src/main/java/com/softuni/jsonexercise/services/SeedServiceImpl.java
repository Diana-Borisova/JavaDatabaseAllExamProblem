package com.softuni.jsonexercise.services;

import com.softuni.jsonexercise.domain.dto.categories.CategoryImportDto;
import com.softuni.jsonexercise.domain.dto.products.ProductImportDto;
import com.softuni.jsonexercise.domain.dto.users.UserImportDto;
import com.softuni.jsonexercise.domain.entities.Category;
import com.softuni.jsonexercise.domain.entities.Product;
import com.softuni.jsonexercise.domain.entities.User;
import com.softuni.jsonexercise.repositories.CategoryRepository;
import com.softuni.jsonexercise.repositories.ProductRepository;
import com.softuni.jsonexercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.softuni.jsonexercise.domain.constant.Paths.*;
import static com.softuni.jsonexercise.domain.constant.Utils.GSON;
import static com.softuni.jsonexercise.domain.constant.Utils.MODEL_MAPPER;
import static java.util.stream.Collectors.*;

@Service
public class SeedServiceImpl implements SeedService{

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws IOException {
       if (this.userRepository.count() == 0){
           final FileReader fileReader = new FileReader(USER_JSON_PATH.toFile());
           final List<User> users = Arrays.stream(GSON.fromJson(fileReader, UserImportDto[].class))
                   .map(userImportDto -> MODEL_MAPPER.map(userImportDto, User.class))
                   .collect(toList());
           this.userRepository.saveAllAndFlush(users);
           fileReader.close();
       }
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(CATEGORY_JSON_PATH.toFile());
            final List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, CategoryImportDto[].class))
                    .map(categoryImportDto -> MODEL_MAPPER.map(categoryImportDto, Category.class))
                    .collect(toList());
            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count()==0) {
            final FileReader fileReader = new FileReader(PRODUCT_JSON_PATH.toFile());
          final List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ProductImportDto[].class))
                    .map(productImportDto -> MODEL_MAPPER.map(productImportDto, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategory)
                    .collect(toList());

          this.productRepository.saveAllAndFlush(products);
            fileReader.close();
        }


    }

    private Product setRandomCategory(Product product) {
        Random random = new Random();
        final int numberOfCategories = random.nextInt((int) this.categoryRepository.count());
       Set<Category> categories = new HashSet<>();

        IntStream.range(0, numberOfCategories)
                .forEach(number -> {
                    Category category = this.categoryRepository.getRandomEntity()
                            .orElseThrow(NoSuchElementException::new);
                    categories.add(category);
                });
product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) >0){
    User buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
    while (buyer.equals(product.getSeller())){
        buyer= this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
    }
    product.setBuyer(buyer);
}

        return product;
    }
    private Product setRandomSeller(Product product) {
        final User seller = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
        product.setSeller(seller);
        return product;
    }
}
