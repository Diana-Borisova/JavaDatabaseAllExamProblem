package com.softuni.gira.entity.services;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceModel {

    private Long id;

    private String username;

    private String password;

    private String email;
}
