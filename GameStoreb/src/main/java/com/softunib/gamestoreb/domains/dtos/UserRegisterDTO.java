package com.softunib.gamestoreb.domains.dtos;

import com.softunib.gamestoreb.domains.entities.User;

import java.util.regex.Pattern;

import static com.softunib.gamestoreb.constants.Validations.*;

public class UserRegisterDTO{


    private  String email;

    private String password;

    private String fullName;

    private String confirmPassword;

    public UserRegisterDTO(String email, String password, String fullName, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.confirmPassword = confirmPassword;
        validate();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }



    private void validate() throws IllegalArgumentException {

        boolean isEmailMatches  = Pattern.matches(PATTERN_EMAIL, this.email);

        if (!isEmailMatches){
            throw new IllegalArgumentException(EMAIL_NOT_VALID_MESSAGE);
        }

        final boolean isPasswordMatches = Pattern.matches(PATTERN_PASSWORD, this.password);

        if (isPasswordMatches) {
            throw new IllegalArgumentException(USERNAME_OR_PASSWORD_NOT_INCORRECT_MESSAGE);
        }

        if (!password.equals(confirmPassword)){
            throw new IllegalArgumentException(PASSWORDS_ARE_NOT_MATCHING);

        }


    }


    public User toUser(){
        return new User(email, password, fullName);
    }
    public String successfulRegisterFormat (){
        return fullName +  " was registered";
    }


    
}
