package com.kairosgames.kairos_games.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(value = {GameNotFoundException.class})
    public ResponseEntity<Object> handleGameNotFoundException
            (GameNotFoundException gameNotFoundException){
        GameException gameException = new GameException(
                gameNotFoundException.getMessage(),
                gameNotFoundException.getCause(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(gameException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {GameBadRequestException.class})
    public ResponseEntity<Object> handlerBadRequestException
            (GameBadRequestException gameBadRequestException){
        GameException gameException = new GameException(
                gameBadRequestException.getMessage(),
                gameBadRequestException.getCause(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(gameException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EmailAlreadyExistException.class})
    public ResponseEntity<Object> handlerEmailAlreadyExist
            (EmailAlreadyExistException emailAlreadyExistException){
        GameException gameException = new GameException(
                emailAlreadyExistException.getMessage(),
                emailAlreadyExistException.getCause(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(gameException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException
            (ForbiddenException forbiddenException){
        GameException gameException = new GameException(
                forbiddenException.getMessage(),
                forbiddenException.getCause(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(gameException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    public ResponseEntity<Object> handleInternalServerErrorException
            (InternalServerErrorException internalServerErrorException){
        GameException gameException = new GameException(
                internalServerErrorException.getMessage(),
                internalServerErrorException.getCause(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(gameException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
