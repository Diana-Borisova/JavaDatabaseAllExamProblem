package com.softuni.jsonexercise.domain.dto.users;

import com.softuni.jsonexercise.domain.dto.products.ProductBasicInfo;
import com.softuni.jsonexercise.domain.dto.products.ProductDto;
import com.softuni.jsonexercise.domain.dto.products.SoldProductsWithCountDto;
import com.softuni.jsonexercise.domain.entities.Product;
import com.softuni.jsonexercise.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String firstName;

    private String lastName;

    private Integer age;

    private Set<ProductDto> sellingProducts;

    private Set<ProductDto> boughtProducts;

    private Set<UserDto> friends;

    public String getFullName(){
        return this.firstName+ " "+ this.lastName;

    }


    public UsersWithProductsDto toUsersWithProductsDto(){
     return    new UsersWithProductsDto(firstName, lastName, age, toSoldProductsWithCountDto());
    }

    public SoldProductsWithCountDto toSoldProductsWithCountDto(){
        return new SoldProductsWithCountDto(sellingProducts.
                stream()
                .map(this::toProductBasicInfo)
                .collect(Collectors.toList()));
    }
    public ProductBasicInfo toProductBasicInfo(ProductDto productDto){
        return new ProductBasicInfo(productDto.getName(), productDto.getPrice());
    }
}
