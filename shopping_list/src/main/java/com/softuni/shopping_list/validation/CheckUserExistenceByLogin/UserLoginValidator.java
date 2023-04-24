package com.softuni.shopping_list.validation.CheckUserExistenceByLogin;


import com.softuni.shopping_list.model.binding.UserLoginBindingModel;
import com.softuni.shopping_list.model.service.UserServiceModel;
import com.softuni.shopping_list.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserLoginValidator implements ConstraintValidator<CheckUserExistence, UserLoginBindingModel> {

    private final UserService userService;

    @Autowired
    public UserLoginValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(CheckUserExistence constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserLoginBindingModel userLoginBindingModel, ConstraintValidatorContext constraintValidatorContext) {
        UserServiceModel user = this.userService.findByUsername(userLoginBindingModel.getUsername());
        return user.getId() != null && user.getPassword().equals(userLoginBindingModel.getPassword());
    }
}
