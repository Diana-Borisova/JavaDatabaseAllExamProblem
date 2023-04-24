package com.softunib.gamestoreb.services.users;

import com.softunib.gamestoreb.domains.entities.User;

public interface UserService {

    String registerUser(String[] args);

    String loginUser(String[] args);
    String logout();

    User getLoggedInUser();
}
