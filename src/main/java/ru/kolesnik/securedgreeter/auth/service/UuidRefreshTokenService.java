package ru.kolesnik.securedgreeter.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolesnik.securedgreeter.auth.config.TokenConfigProperties;
import ru.kolesnik.securedgreeter.auth.exception.NotFoundException;
import ru.kolesnik.securedgreeter.auth.exception.RefreshTokenExpiredException;
import ru.kolesnik.securedgreeter.auth.model.RefreshToken;
import ru.kolesnik.securedgreeter.auth.model.User;
import ru.kolesnik.securedgreeter.auth.repository.RefreshTokenRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
                .expirationTime(
                        LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(System.currentTimeMillis() +
                                        tokenConfigProperties.getRefresh().getDuration().toMillis()),
                                ZoneId.systemDefault()
                        )
                )
                .owner(user)
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken updateToken(String token) {
        final RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Refresh token does not exist!"));
        refreshTokenRepository.delete(oldRefreshToken);
        if (isTokenExpired(oldRefreshToken)) {
            throw new RefreshTokenExpiredException();
        }
        RefreshToken newRefreshToken = RefreshToken
                .builder()
                .token(UUID.randomUUID().toString())
                .expirationTime(
                        LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(System.currentTimeMillis() +
                                        tokenConfigProperties.getRefresh().getDuration().toMillis()),
                                ZoneId.systemDefault()
                        )
                )
                .owner(oldRefreshToken.getOwner())
                .build();
        refreshTokenRepository.save(newRefreshToken);
        return newRefreshToken;
    }

    private boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpirationTime().isBefore(LocalDateTime.now());
    }

}
