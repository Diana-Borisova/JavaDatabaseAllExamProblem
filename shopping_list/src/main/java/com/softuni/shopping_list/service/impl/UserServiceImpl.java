package com.softuni.shopping_list.service.impl;

import com.softuni.shopping_list.model.entity.LoggedUser;
import com.softuni.shopping_list.model.entity.User;
import com.softuni.shopping_list.model.service.UserServiceModel;
import com.softuni.shopping_list.repository.UserRepository;
import com.softuni.shopping_list.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoggedUser loggedUser;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, LoggedUser loggedUser, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void loginUser(Long id) {
        loggedUser.setId(id);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

            this.userRepository.save(modelMapper.map(userServiceModel, User.class));

    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.modelMapper.map(this.userRepository.findByUsername(username).orElse(new User()), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByUsernameOrEmail(String username, String email) {
        return this.modelMapper.map(this.userRepository.findByUsernameOrEmail(username,email).orElse(new User()), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password){
        return this.userRepository
                .findByUsernameAndPassword(username,password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }


}
