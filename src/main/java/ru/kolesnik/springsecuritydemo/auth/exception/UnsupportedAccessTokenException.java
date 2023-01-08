package ru.kolesnik.springsecuritydemo.auth.exception;

public class UnsupportedAccessTokenException extends RuntimeException {

    public UnsupportedAccessTokenException() {
        super("Access token not supported!");
    }

}
