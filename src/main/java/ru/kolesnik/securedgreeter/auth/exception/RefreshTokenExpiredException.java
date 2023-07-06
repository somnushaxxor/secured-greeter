package ru.kolesnik.securedgreeter.auth.exception;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException() {
        super("Refresh token expired!");
    }

}
