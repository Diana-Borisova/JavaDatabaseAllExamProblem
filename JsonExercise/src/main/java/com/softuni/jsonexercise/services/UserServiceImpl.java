package com.softuni.jsonexercise.services;

import com.softuni.jsonexercise.domain.dto.users.UserDto;
import com.softuni.jsonexercise.domain.dto.users.UsersAndProductsWrapperDto;
import com.softuni.jsonexercise.domain.dto.users.UsersWithProductsDto;
import com.softuni.jsonexercise.domain.dto.users.UsersWithSoldProductsDto;
import com.softuni.jsonexercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.softuni.jsonexercise.domain.constant.Paths.SOLD_PRODUCTS_PATH;
import static com.softuni.jsonexercise.domain.constant.Paths.USERS_AND_PRODUCTS_PATH;
import static com.softuni.jsonexercise.domain.constant.Utils.MODEL_MAPPER;
import static com.softuni.jsonexercise.domain.constant.Utils.writeJsonIntoFile;

@Service
public class UserServiceImpl implements UserService{
   final private UserRepository userRepository;
@Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UsersWithSoldProductsDto> findAllByOrderByLastNameAscFirstNameAsc() throws IOException {
        List<UsersWithSoldProductsDto> usersWithSoldProductsDtoList = this.userRepository
                .findAllByOrderByLastNameAscFirstNameAsc()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER
                        .map(user, UsersWithSoldProductsDto.class))
                .collect(Collectors.toList());

        writeJsonIntoFile(usersWithSoldProductsDtoList, SOLD_PRODUCTS_PATH);

        return usersWithSoldProductsDtoList;
    }

@Override
    public UsersAndProductsWrapperDto usersAndProducts() throws IOException {
        List<UsersWithProductsDto> usersAndProducts = this.userRepository
                .findAllByOrderByLastNameAscFirstNameAsc()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER
                        .map(user, UserDto.class))
                .map(UserDto:: toUsersWithProductsDto)
                .collect(Collectors.toList());
       final UsersAndProductsWrapperDto usersAndProductsWrapperDto = new UsersAndProductsWrapperDto(usersAndProducts);
        writeJsonIntoFile(usersAndProducts, USERS_AND_PRODUCTS_PATH);

        return usersAndProductsWrapperDto;
    }
}
