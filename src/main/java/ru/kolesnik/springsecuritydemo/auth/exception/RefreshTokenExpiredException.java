package ru.kolesnik.springsecuritydemo.auth.exception;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException() {
        super("Refresh token expired!");
    }

}
