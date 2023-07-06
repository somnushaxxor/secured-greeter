package ru.kolesnik.securedgreeter.auth.service;

import ru.kolesnik.securedgreeter.auth.model.RefreshToken;
import ru.kolesnik.securedgreeter.auth.model.User;

public interface RefreshTokenService {

    RefreshToken generateToken(User user);

    RefreshToken updateToken(String token);

}
