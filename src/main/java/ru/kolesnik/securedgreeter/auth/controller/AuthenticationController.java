package ru.kolesnik.securedgreeter.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolesnik.securedgreeter.auth.dto.RefreshTokenRequest;
import ru.kolesnik.securedgreeter.auth.dto.SignInRequest;
import ru.kolesnik.securedgreeter.auth.dto.SignUpRequest;
import ru.kolesnik.securedgreeter.auth.dto.TokenResponse;
import ru.kolesnik.securedgreeter.auth.service.AuthenticationService;
import ru.kolesnik.securedgreeter.auth.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody SignUpRequest request) {
        userService.addUser(request);
    }

    @PostMapping("/sign-in")
    public TokenResponse signIn(@Valid @RequestBody SignInRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authenticationService.refreshToken(request);
    }

}
