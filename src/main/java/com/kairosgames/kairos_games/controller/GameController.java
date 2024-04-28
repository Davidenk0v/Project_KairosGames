package com.kairosgames.kairos_games.controller;

import com.kairosgames.kairos_games.JaccardSimilarity;
import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.service.GameService;

import com.kairosgames.kairos_games.service.TrendingService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class GameController {

    private GameService service;
    private TrendingService trendingService;

    public GameController(GameService service, TrendingService trendingService)
    {
        this.service = service;
        this.trendingService = trendingService;
    }


    // Esto aquí en provisional
    @GetMapping("/")
    public ResponseEntity<String> loadingDatabase() {
        try{
            this.service.loadDatabase();
            this.trendingService.loadDatabase();
            return new ResponseEntity<>("Cargando", HttpStatus.OK);
        }catch(Exception e){
            throw new RuntimeException();
        }

    }


    /* http://localhost:8080/api/games?page=2 */
    @GetMapping("/games")
    public ResponseEntity<Page<Game>> getAllPage(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
         Page<Game> gamePage = this.service.findAll(pageable);
        return ResponseEntity.ok(gamePage);
    }

    @GetMapping("/games/filter/{name}")
    public ResponseEntity<List<Game>> filterByName(@PathVariable String name) {
        String halfLength = name.substring(0, name.length() / 2);
        List<Game> games = this.service.filterByname(halfLength);
        List<Game> gameSimi = JaccardSimilarity.jaccardSimilarity(name, games);
         return ResponseEntity.ok(gameSimi);
    }

    @GetMapping("/games/search/{name}")
    public ResponseEntity<List<Game>> searchByName(@PathVariable String name) {
        List<Game> games = this.service.filterByname(name);
         return ResponseEntity.ok(games);
    }
    
    
    @GetMapping("/games/trending")
    public ResponseEntity<List<Game>> getTrendingGames(){
        return ResponseEntity.ok(this.trendingService.findAll());
    }


    @GetMapping("/games/{id}")
    public ResponseEntity<Game> findById(@PathVariable Long id) {
            return ResponseEntity.ok(this.service.findById(id).get());
        }


    @GetMapping("/games/name/{name}")
    public ResponseEntity<List<Game>> findByname(@PathVariable String name) {
        return ResponseEntity.ok(service.findByname(name));
    }


    @PostMapping("/games")
    public ResponseEntity<Game> create(@RequestBody Game game) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(game));
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        this.service.deleteById(id);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminated game");
    }

}