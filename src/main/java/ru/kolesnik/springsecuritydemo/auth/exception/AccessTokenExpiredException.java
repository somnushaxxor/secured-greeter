package ru.kolesnik.springsecuritydemo.auth.exception;

public class AccessTokenExpiredException extends RuntimeException {

    public AccessTokenExpiredException() {
        super("Access token expired!");
    }

}
