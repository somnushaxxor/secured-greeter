package ru.kolesnik.securedgreeter;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorMessage {

    private final String message;
    private final LocalDateTime timestamp;

    public ErrorMessage(String message) {
        this.message = message;
        timestamp = LocalDateTime.now();
    }

}
