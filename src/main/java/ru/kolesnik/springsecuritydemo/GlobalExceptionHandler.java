package ru.kolesnik.springsecuritydemo;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kolesnik.springsecuritydemo.auth.exception.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ErrorMessage handleEmailAlreadyInUseException(EmailAlreadyInUseException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ErrorMessage handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ErrorMessage handleRefreshTokenExpiredException(RefreshTokenExpiredException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessTokenExpiredException.class)
    public ErrorMessage handleAccessTokenExpiredException(AccessTokenExpiredException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnsupportedAccessTokenException.class)
    public ErrorMessage handleUnsupportedAccessTokenException(UnsupportedAccessTokenException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorMessage handleAuthenticationException(AuthenticationException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleException(Exception e) {
        return new ErrorMessage(e.getMessage());
    }

}
