package com.kairosgames.kairos_games.controller_exceptions;

public class NotFoundException extends BadRequestException {

    private static final String DESCRIPTION = "Bad Request Exception (400)";

    public NotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
