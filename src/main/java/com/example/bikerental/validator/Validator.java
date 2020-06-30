package com.example.bikerental.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Logger LOGGER = LogManager.getLogger(Validator.class);

    private final static String LOGIN_REGEX = "[a-zA-Z]{1}[a-zA-Z0-9]{2,20}";
    private final static String NAME_REGEX = "[a-zA-Zа-яА-ЯЁё]{3,15}";
    private final static String EMAIL_REGEX = "[a-zA-Z]{1}\\w{1,15}@[a-zA-Z]{1,10}\\.[a-z]{2,3}";
    private final static String PASSWORD_REGEX = "^[^ ]{8,20}$";


    public static boolean validateLogin(String login) {
        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validateName(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        return  matcher.matches();
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
