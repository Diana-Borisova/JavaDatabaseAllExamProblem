package com.softuni.jsonexercise.services;

import com.softuni.jsonexercise.domain.dto.users.UsersAndProductsWrapperDto;
import com.softuni.jsonexercise.domain.dto.users.UsersWithSoldProductsDto;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UsersWithSoldProductsDto> findAllByOrderByLastNameAscFirstNameAsc() throws IOException;

    UsersAndProductsWrapperDto usersAndProducts() throws IOException;
}
