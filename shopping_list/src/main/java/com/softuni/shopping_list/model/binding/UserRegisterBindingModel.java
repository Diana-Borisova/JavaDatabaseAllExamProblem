package com.softuni.shopping_list.model.binding;




import com.softuni.shopping_list.validation.CheckUserExistenceByRegister.CheckUsernameAlreadyExists;
import com.softuni.shopping_list.validation.PasswordMatcher.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CheckUsernameAlreadyExists
public class UserRegisterBindingModel {

    @NotNull()
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;

    @NotBlank()
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;

    @NotBlank()
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String confirmPassword;

    @Email
    @NotBlank(message = "Email can not be empty!")
    private String email;


}
