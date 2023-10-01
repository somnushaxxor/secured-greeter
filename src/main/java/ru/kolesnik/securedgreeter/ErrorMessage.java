package ru.kolesnik.securedgreeter;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorMessage {

    private final String message;
    private final Instant timestamp;

    public ErrorMessage(String message) {
        this.message = message;
        timestamp = Instant.now();
    }

}
