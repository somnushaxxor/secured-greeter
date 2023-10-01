package ru.kolesnik.securedgreeter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kolesnik.securedgreeter.auth.exception.AccessTokenException;
import ru.kolesnik.securedgreeter.auth.exception.EmailAlreadyInUseException;
import ru.kolesnik.securedgreeter.auth.exception.NotFoundException;
import ru.kolesnik.securedgreeter.auth.exception.RefreshTokenExpiredException;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
    public void handleAccessTokenException(AccessTokenException e) {
    }

}
