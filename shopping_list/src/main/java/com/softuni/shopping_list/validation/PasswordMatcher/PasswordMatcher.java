package com.softuni.shopping_list.validation.PasswordMatcher;

import com.softuni.shopping_list.model.binding.UserRegisterBindingModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatcher implements ConstraintValidator<PasswordMatch, UserRegisterBindingModel> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserRegisterBindingModel userRegisterBindingModel, ConstraintValidatorContext constraintValidatorContext) {
        if(userRegisterBindingModel.getPassword() != null && userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                .addPropertyNode(userRegisterBindingModel.getConfirmPassword())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
