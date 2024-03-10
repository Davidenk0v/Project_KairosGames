package com.kairosgames.kairos_games.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class GameException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus htttpStatus;
    private final ZonedDateTime timestamp;

    public GameException(String message, Throwable throwable, HttpStatus htttpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.throwable = throwable;
        this.htttpStatus = htttpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHtttpStatus() {
        return htttpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
    
}
