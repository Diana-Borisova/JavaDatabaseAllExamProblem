package com.softuni.jsonexercise;

import com.softuni.jsonexercise.services.CategoryService;
import com.softuni.jsonexercise.services.ProductService;
import com.softuni.jsonexercise.services.SeedService;
import com.softuni.jsonexercise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
public class CommandRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;

    private final UserService userService;
    private final CategoryService categoryService;
    @Autowired
    public CommandRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Task 2.	Seed the Database
       // this.seedService.seedAll();

        // Task 3.	Query and Export Data
             // 3.1 - Products in Range
        // this.productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice( BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

            // 3.2 - Successfully Sold Products
       // this.userService.findAllByOrderByLastNameAscFirstNameAsc();

            // 3.3 - Categories by Products Count
       // this.categoryService.getCategorySummary();

            // 3.4 - Users and Products
        // this.userService.usersAndProducts();
    }
}
