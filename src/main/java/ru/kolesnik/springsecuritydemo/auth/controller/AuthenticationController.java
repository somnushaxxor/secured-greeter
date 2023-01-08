package ru.kolesnik.springsecuritydemo.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolesnik.springsecuritydemo.auth.dto.RefreshTokenRequest;
import ru.kolesnik.springsecuritydemo.auth.dto.SignInRequest;
import ru.kolesnik.springsecuritydemo.auth.dto.SignUpRequest;
import ru.kolesnik.springsecuritydemo.auth.dto.TokenResponse;
import ru.kolesnik.springsecuritydemo.auth.service.AuthenticationService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody SignUpRequest request) {
        authenticationService.addUser(request);
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
