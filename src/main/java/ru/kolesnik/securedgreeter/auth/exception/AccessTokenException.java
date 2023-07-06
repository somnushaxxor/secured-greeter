package ru.kolesnik.securedgreeter.auth.exception;

public class AccessTokenException extends RuntimeException {

    public AccessTokenException(Throwable cause) {
        super(cause);
    }

}
