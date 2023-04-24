package com.softuni.shopping_list.service;


import com.softuni.shopping_list.model.service.UserServiceModel;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {


    void loginUser(Long id);

    void registerUser(UserServiceModel userServiceModel);


    UserServiceModel findByUsername(String username);
    UserServiceModel findByUsernameOrEmail(String username, String email);

    UserServiceModel findByUsernameAndPassword(String username, String password);

}
