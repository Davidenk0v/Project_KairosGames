package com.kairosgames.kairos_games.model;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
