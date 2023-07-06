package ru.kolesnik.securedgreeter.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kolesnik.securedgreeter.auth.exception.AccessTokenExpiredException;
import ru.kolesnik.securedgreeter.auth.exception.UnsupportedAccessTokenException;

import java.util.Date;

public interface AccessTokenService {

    String extractUsername(String token);

    Date extractExpiration(String token);

    void checkTokenValid(String token) throws AccessTokenExpiredException, UnsupportedAccessTokenException;

    String generateToken(UserDetails userDetails);

}
