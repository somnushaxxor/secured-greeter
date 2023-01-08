package ru.kolesnik.springsecuritydemo.auth.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found!");
    }

}
