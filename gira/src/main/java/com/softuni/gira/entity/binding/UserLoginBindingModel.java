package com.softuni.gira.entity.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginBindingModel {

    private Long id;

    @NotNull(message = "Email cannot be empty!")
    @Email( message = "Incorrect email or password!")
    private String email;


    @NotNull(message = "Password cannot be empty!")
    @Size(min= 5, max= 20,message = "Incorrect email or password!")
    private String password;
}
