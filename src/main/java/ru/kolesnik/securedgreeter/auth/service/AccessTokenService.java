package ru.kolesnik.securedgreeter.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kolesnik.securedgreeter.auth.exception.AccessTokenException;

public interface AccessTokenService {

    String extractUsername(String token);

    void checkTokenValidity(String token) throws AccessTokenException;

    String generateToken(UserDetails userDetails);

}
