package ru.kolesnik.securedgreeter.auth.exception;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException() {
        super("Email already in use!");
    }

}
