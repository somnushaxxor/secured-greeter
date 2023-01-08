package ru.kolesnik.springsecuritydemo.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolesnik.springsecuritydemo.auth.config.TokenConfigProperties;
import ru.kolesnik.springsecuritydemo.auth.exception.RefreshTokenExpiredException;
import ru.kolesnik.springsecuritydemo.auth.exception.RefreshTokenNotFoundException;
import ru.kolesnik.springsecuritydemo.auth.model.RefreshToken;
import ru.kolesnik.springsecuritydemo.auth.model.User;
import ru.kolesnik.springsecuritydemo.auth.repository.RefreshTokenRepository;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UuidRefreshTokenService implements RefreshTokenService {

    private final TokenConfigProperties tokenConfigProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateToken(User user) {
        RefreshToken refreshToken = RefreshToken
                .builder()
                .token(UUID.randomUUID().toString())
                .expirationDate(
                        new Date(System.currentTimeMillis() + tokenConfigProperties.getRefresh().getDuration().toMillis())
                )
                .owner(user)
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken updateToken(String token) {
        final RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);
        refreshTokenRepository.delete(oldRefreshToken);
        if (isTokenExpired(oldRefreshToken)) {
            throw new RefreshTokenExpiredException();
        }
        RefreshToken newRefreshToken = RefreshToken
                .builder()
                .token(UUID.randomUUID().toString())
                .expirationDate(
                        new Date(System.currentTimeMillis() + tokenConfigProperties.getRefresh().getDuration().toMillis())
                )
                .owner(oldRefreshToken.getOwner())
                .build();
        refreshTokenRepository.save(newRefreshToken);
        return newRefreshToken;
    }

    private boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpirationDate().before(new Date());
    }

}
