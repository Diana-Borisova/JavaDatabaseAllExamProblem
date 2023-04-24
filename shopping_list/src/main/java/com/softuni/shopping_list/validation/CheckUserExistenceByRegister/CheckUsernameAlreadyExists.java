package com.softuni.shopping_list.validation.CheckUserExistenceByRegister;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UserRegisterValidator.class)
public @interface CheckUsernameAlreadyExists {

    String message() default "This username already exists!";

    Class <?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
