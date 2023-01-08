package ru.kolesnik.springsecuritydemo.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolesnik.springsecuritydemo.auth.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

}
