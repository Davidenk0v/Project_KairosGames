package com.kairosgames.kairos_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kairosgames.kairos_games.controller_exceptions.ApiExceptionHandler;
import com.kairosgames.kairos_games.controller_exceptions.ErrorMessage;
import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.service.GameService;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class GameController {

    private GameService service;
    private ApiExceptionHandler exception;

    @Autowired

    // Esto aquí en provisional
    @RequestMapping("/")
    public void loadingDatabase() {
        List<Game> gameList = this.service.loadDatabase();
        for (Game game : gameList) {
            List<Game> sameName = service.findByname(game.getName()); // Guarda en esta lista los juegos que se
                                                                      // encuentren en la base de datos
            if (sameName.isEmpty()) {
                this.service.save(game); // Si no encuentra ninguno juego con ese nombre lo añade a la base de datos
            } else {
                for (Game game2 : sameName) {
                    if (game2.getPlatform().equals(game.getPlatform())) {
                        this.service.save(game);
                    } else {
                        game2.setActualPrice(game.getActualPrice()); // Si encuentra alguno le actualiza el precio
                        this.service.save(game2);
                    }
                }
            }
        }
    }

    /*
     * GET http://localhost:8080/api/games
     */

    @RequestMapping("/games")
    public ResponseEntity<List<Game>> getAllGames() {

        List<Game> games = service.findAll();
        if (games.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(games);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> findById(@PathVariable Long id) {

        return this.service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //ESTE METODO SE DEBE ELIMINAR SOLO PARA TESTEAR
    @GetMapping("/games/name/{name}")
    public ResponseEntity<List<Game>> findByname(@PathVariable String name) {
        List<Game> games = service.findByname(name);
        if (games.isEmpty()){
            //return ResponseEntity.notFound().build();
            return throw new ErrorMessage(null, name);
        }

        return ResponseEntity.ok(games);
    }


    @PostMapping("/games")
    public ResponseEntity<Game> create(@RequestBody Game game) {
        if (game.getId() != null)
            return ResponseEntity.badRequest().build();

        this.service.save(game);
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Game> deleteById(@PathVariable Long id) {
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}