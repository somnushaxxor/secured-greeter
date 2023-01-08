package ru.kolesnik.springsecuritydemo.auth.service;

import ru.kolesnik.springsecuritydemo.auth.model.RefreshToken;
import ru.kolesnik.springsecuritydemo.auth.model.User;

public interface RefreshTokenService {

    RefreshToken generateToken(User user);

    RefreshToken updateToken(String token);

}
