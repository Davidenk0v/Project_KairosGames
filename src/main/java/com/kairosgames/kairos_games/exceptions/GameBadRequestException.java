package com.kairosgames.kairos_games.exceptions;

public class GameBadRequestException extends RuntimeException {
    
    public GameBadRequestException(String message) {
        super(message);
    }

    public GameBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
