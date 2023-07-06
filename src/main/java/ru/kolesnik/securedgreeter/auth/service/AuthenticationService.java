package ru.kolesnik.securedgreeter.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kolesnik.securedgreeter.auth.dto.RefreshTokenRequest;
import ru.kolesnik.securedgreeter.auth.dto.SignInRequest;
import ru.kolesnik.securedgreeter.auth.dto.SignUpRequest;
import ru.kolesnik.securedgreeter.auth.dto.TokenResponse;
import ru.kolesnik.securedgreeter.auth.exception.EmailAlreadyInUseException;
import ru.kolesnik.securedgreeter.auth.exception.UserNotFoundException;
import ru.kolesnik.securedgreeter.auth.model.RefreshToken;
import ru.kolesnik.securedgreeter.auth.model.User;
import ru.kolesnik.securedgreeter.auth.repository.RoleRepository;
import ru.kolesnik.securedgreeter.auth.repository.UserRepository;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    public void addUser(SignUpRequest request) {
        if (isEmailAlreadyInUse(request.getEmail())) {
            throw new EmailAlreadyInUseException();
        }
        final User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(roleRepository.findByName("ROLE_USER").get()))
                .build();
        userRepository.save(user);
    }

    public TokenResponse authenticate(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        String accessToken = accessTokenService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.generateToken(user);
        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    public TokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken newRefreshToken = refreshTokenService.updateToken(request.getRefreshToken());
        String accessToken = accessTokenService.generateToken(newRefreshToken.getOwner());
        return new TokenResponse(accessToken, newRefreshToken.getToken());
    }

    private boolean isEmailAlreadyInUse(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
