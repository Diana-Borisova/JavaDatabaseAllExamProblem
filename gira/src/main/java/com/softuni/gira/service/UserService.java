package com.softuni.gira.service;


import com.softuni.gira.entity.User;
import com.softuni.gira.entity.services.UserServiceModel;

public interface UserService {
    UserServiceModel findUserByUsername(String username);

    void registerUser(UserServiceModel userServiceModel);

    void loginUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsernameAndPassword(String username, String password);

    User findById(Long id);

    void deleteById(Long id);

    UserServiceModel findByEmailAndPassword (String email, String password);



}
