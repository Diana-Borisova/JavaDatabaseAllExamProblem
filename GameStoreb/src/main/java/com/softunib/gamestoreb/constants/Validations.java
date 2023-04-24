package com.softunib.gamestoreb.constants;

public enum  Validations {
    ;
    public static final String PATTERN_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    public static final String PATTERN_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";




    public static final String EMAIL_NOT_VALID_MESSAGE = "Incorrect email.";
    public static final String USERNAME_OR_PASSWORD_NOT_INCORRECT_MESSAGE= "Incorrect username / password";
    public static final String PASSWORDS_ARE_NOT_MATCHING = "Passwords are not matching";
    public static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found";
}
