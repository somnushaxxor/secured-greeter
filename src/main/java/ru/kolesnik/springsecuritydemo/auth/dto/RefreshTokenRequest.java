package ru.kolesnik.springsecuritydemo.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenRequest {

    @JsonProperty("refresh_token")
    @NotBlank
    private String refreshToken;

}
