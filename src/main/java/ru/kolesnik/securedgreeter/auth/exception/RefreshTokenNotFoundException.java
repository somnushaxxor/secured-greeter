package ru.kolesnik.securedgreeter.auth.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

    public RefreshTokenNotFoundException() {
        super("Refresh token does not exist!");
    }

}
