package com.softuni.gira.entity.binding;

import jakarta.persistence.Column;
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
public class UserRegisterBindingModel {
    @NotNull(message = "Username cannot be empty")
    @Size(min=3, max = 20, message = "Username length must be between 3 and 20 characters (inclusive 3 and 20).")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Size(min=3, max = 20, message = "Password length must be between 3 and 20 characters (inclusive 3 and 20).")
    private String password;

    @NotNull(message = "Password cannot be empty")
    @Size(min=3, max = 20, message = "Password length must be between 3 and 20 characters (inclusive 3 and 20).")
    private String confirmPassword;

    @NotNull(message = "Email cannot be empty.")
    @Email
    private String email;
}
