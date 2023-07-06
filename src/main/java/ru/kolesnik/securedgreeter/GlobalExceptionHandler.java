package ru.kolesnik.securedgreeter;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kolesnik.securedgreeter.auth.exception.AccessTokenException;
import ru.kolesnik.securedgreeter.auth.exception.EmailAlreadyInUseException;
import ru.kolesnik.securedgreeter.auth.exception.NotFoundException;
import ru.kolesnik.securedgreeter.auth.exception.RefreshTokenExpiredException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Something went wrong :(";

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ErrorMessage handleEmailAlreadyInUseException(EmailAlreadyInUseException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage handleNotFoundException(NotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ErrorMessage handleRefreshTokenExpiredException(RefreshTokenExpiredException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessTokenException.class)
    public ErrorMessage handleAccessTokenException(AccessTokenException e) {
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
        return new ErrorMessage(INTERNAL_SERVER_ERROR_MESSAGE);
    }

}
