package com.kairosgames.kairos_games.controller;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.service.GameService;
//import com.kairosgames.kairos_games.service.TrendingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class GameController {

    private GameService service;
    //private TrendingService trendingService;

    public GameController(GameService service) {
        this.service = service;
    }


    // Esto aquí en provisional
    @GetMapping("/")
    public void loadingDatabase() {
        this.service.loadDatabase();
    }

    /*
     * GET http://localhost:8080/api/games
     */
    
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok(this.service.findAll());
    }

/*     @GetMapping("/games/trending")
    public ResponseEntity<Set<String>> getTrendingGames(){
        return ResponseEntity.ok(this.trendingService.findAll());
    } */

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> findById(@PathVariable Long id) {
            return ResponseEntity.ok(this.service.findById(id).get());
        }


    //ESTE METODO SE DEBE ELIMINAR SOLO PARA TESTEAR
    @GetMapping("/games/name/{name}")
    public ResponseEntity<List<Game>> findByname(@PathVariable String name) {
        return ResponseEntity.ok(service.findByname(name));
    }


    @PostMapping("/games")
    public ResponseEntity<Game> create(@RequestBody Game game) {
        return ResponseEntity.ok(this.service.save(game));
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        this.service.deleteById(id);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminated game");
    }

}