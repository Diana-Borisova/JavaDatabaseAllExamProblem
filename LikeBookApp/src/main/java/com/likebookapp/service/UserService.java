package com.likebookapp.service;

import com.likebookapp.model.entity.User;
import com.likebookapp.model.service.UserServiceModel;
import org.apache.coyote.http11.Http11AprProtocol;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface UserService {

    public UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsernameAndPassword(String username, String password);

    void loginUser(Long id);

    User findById(Long id);

    Optional<User> findUserById(Long id);

    public void logout(HttpSession httpSession);
}
