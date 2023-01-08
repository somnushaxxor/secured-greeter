package ru.kolesnik.springsecuritydemo.auth.exception;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException() {
        super("Email already in use!");
    }

}
