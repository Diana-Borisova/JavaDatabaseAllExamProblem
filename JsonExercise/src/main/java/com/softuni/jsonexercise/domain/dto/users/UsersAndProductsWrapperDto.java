package com.softuni.jsonexercise.domain.dto.users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class UsersAndProductsWrapperDto {
    private Integer usersCount;
    private List<UsersWithProductsDto> users;

    public UsersAndProductsWrapperDto(List<UsersWithProductsDto> users) {
        this.users = users;
        this.usersCount=users.size();
    }
}
