package ru.kolesnik.springsecuritydemo.auth.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class SignInRequest {

    @Email
    private String email;
    @NotBlank
    private String password;

}
