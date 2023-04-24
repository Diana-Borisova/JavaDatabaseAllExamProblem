package com.softuni.jsonexercise.domain.dto.users;

import com.softuni.jsonexercise.domain.dto.products.SoldProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithSoldProductsDto {
    private String firstName;

    private String lastName;
    private List<SoldProductDto> soldProducts;
}
