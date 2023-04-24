package com.softuni.shopping_list.validation.CheckUserExistenceByRegister;


import com.softuni.shopping_list.model.binding.UserLoginBindingModel;
import com.softuni.shopping_list.model.binding.UserRegisterBindingModel;
import com.softuni.shopping_list.model.service.UserServiceModel;
import com.softuni.shopping_list.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserRegisterValidator implements ConstraintValidator<CheckUsernameAlreadyExists, UserRegisterBindingModel> {

    private final UserService userService;

    @Autowired
    public UserRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(CheckUsernameAlreadyExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserRegisterBindingModel userRegisterBindingModel, ConstraintValidatorContext constraintValidatorContext) {
        UserServiceModel user = this.userService.findByUsernameOrEmail
                (userRegisterBindingModel.getUsername(), userRegisterBindingModel.getEmail());
        return user.getId() == null;
    }
}
