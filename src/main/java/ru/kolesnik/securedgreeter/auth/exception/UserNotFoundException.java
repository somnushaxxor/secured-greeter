package ru.kolesnik.securedgreeter.auth.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found!");
    }

}
