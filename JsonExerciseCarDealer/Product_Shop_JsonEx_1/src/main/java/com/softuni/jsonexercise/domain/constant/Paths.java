package com.softuni.jsonexercise.domain.constant;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path USER_JSON_PATH = Path.of("src","main", "resources", "dbContent", "users.json");
    public static final Path CATEGORY_JSON_PATH = Path.of("src","main", "resources", "dbContent", "categories.json");

    public static final Path PRODUCT_JSON_PATH = Path.of("src","main", "resources", "dbContent", "products.json");
    public static final Path PRODUCTS_WITH_NO_BUYERS_PATH = Path.of("src","main", "resources", "output", "products-in-range.json");

    public static final Path SOLD_PRODUCTS_PATH = Path.of("src","main", "resources", "output", "users-sold-products.json");

    public static final Path CATEGORY_PATH = Path.of("src","main", "resources", "output", "categories-by-products.json");
    public static final Path USERS_AND_PRODUCTS_PATH = Path.of("src","main", "resources", "output", "users-and-products.json");
}
