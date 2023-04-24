package com.softuni.gira.service.impl;

import com.softuni.gira.entity.User;
import com.softuni.gira.entity.services.UserServiceModel;
import com.softuni.gira.repository.UserRepository;
import com.softuni.gira.service.UserService;
import com.softuni.gira.utils.LoggedUser;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Getter
@Setter
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
    public UserServiceModel findUserByUsername(String username) {
        return modelMapper.map(this.userRepository.findUserByUsername(username),UserServiceModel.class );
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = new User();
        user.setUsername(userServiceModel.getUsername());
        user.setEmail(userServiceModel.getEmail());
        user.setPassword(userServiceModel.getPassword());
        this.userRepository.save(user);
    }

    @Override
    public void loginUser(UserServiceModel userServiceModel) {
        this.userRepository
                .findUserByUsernameAndPassword(userServiceModel.getUsername(),
                        userServiceModel.getPassword()).ifPresent(user -> loggedUser.setId(user.getId()));

    }

    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {
        return modelMapper.map(this.userRepository.findUserByUsernameAndPassword(username, password), UserServiceModel.class);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public UserServiceModel findByEmailAndPassword(String email, String password) {
        return modelMapper.map(this.userRepository.findByEmailAndPassword(email, password), UserServiceModel.class);
    }
}
