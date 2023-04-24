package com.softuni.jsonexercise.domain.dto.products;

import com.softuni.jsonexercise.domain.dto.users.UserDto;
import com.softuni.jsonexercise.domain.dto.users.UsersWithProductsDto;
import com.softuni.jsonexercise.domain.entities.Category;
import com.softuni.jsonexercise.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String name;

    private BigDecimal price;


    private UserDto buyer;


    private UserDto seller;


    private Set<Category> categories;

    public ProductNoBuyerDto toProductNoBuyerDto() {
        return new ProductNoBuyerDto(name, price, seller.getFullName());
    }

}