package com.kairosgames.kairos_games.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kairosgames.kairos_games.GameScrapper;
import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.service.GameService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GameController {

    private GameService service;
    private GameScrapper scrapper;

    public GameController(GameService service, GameScrapper scrapper) {
        this.scrapper = scrapper;
        this.service = service;
    }

    /*
    GET http://localhost:8080/api/games
     */
    @RequestMapping("/games")
    public ResponseEntity<List<Game>> findAll(){
        List<Game> games = scrapper.getAll();
        if(games.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(games);
    }

    @RequestMapping("/games/{name}")
    public ResponseEntity<List<Game>> findByname(@PathVariable String name){
        List<Game> games = service.findByname(name);
        if(games.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(games);
    }

    @RequestMapping("/games/{id}")
    public ResponseEntity<Game> findById(@PathVariable Long id){

        return this.service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping("/prueba")
    public ResponseEntity<List<Game>> prueba(){
        List<Game> games = scrapper.g2aDestacados();
        if(games.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(games);
    }

    @PostMapping("/games")
    public ResponseEntity<Game> create (@RequestBody Game game){
        if(game.getId() != null)
            return ResponseEntity.badRequest().build();

        this.service.save(game);
        return ResponseEntity.ok(game);
    }

    @PutMapping("/games")
    public ResponseEntity<Game> update (@RequestBody Game game){
        this.service.save(game);
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Game> deleteById (@PathVariable Long id){
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}