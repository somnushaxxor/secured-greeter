package ru.kolesnik.securedgreeter.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.kolesnik.securedgreeter.auth.dto.RefreshTokenRequest;
import ru.kolesnik.securedgreeter.auth.dto.SignInRequest;
import ru.kolesnik.securedgreeter.auth.dto.TokenResponse;
import ru.kolesnik.securedgreeter.auth.model.RefreshToken;
import ru.kolesnik.securedgreeter.auth.model.User;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    public TokenResponse authenticate(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = (User) userService.loadUserByUsername(request.getEmail());
        String accessToken = accessTokenService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.generateToken(user);
        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    public TokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken newRefreshToken = refreshTokenService.updateToken(request.getRefreshToken());
        String accessToken = accessTokenService.generateToken(newRefreshToken.getOwner());
        return new TokenResponse(accessToken, newRefreshToken.getToken());
    }

}
