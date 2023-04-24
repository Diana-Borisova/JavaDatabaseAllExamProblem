package com.likebookapp.service.impl;

import com.likebookapp.model.entity.User;
import com.likebookapp.model.service.UserServiceModel;
import com.likebookapp.repository.UserRepository;
import com.likebookapp.service.UserService;
import com.likebookapp.util.LoggedUser;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Getter
@Setter
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final LoggedUser loggedUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

        this.loggedUser = loggedUser;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        return modelMapper.map(userRepository.save(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        return userRepository.
                findByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user,UserServiceModel.class)).orElse(null);
    }

    @Override
    public void loginUser(Long id) {
        loggedUser.setId(id);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public void logout(HttpSession httpSession) {

            httpSession.invalidate();
            this.loggedUser.setId(null);
            this.loggedUser.setUsername(null);

    }


}

