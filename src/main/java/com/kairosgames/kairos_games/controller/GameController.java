package com.kairosgames.kairos_games.controller;

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
@CrossOrigin(origins = "*")
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
    public void loadingDatabase() {
        this.service.loadDatabase();
        this.trendingService.loadDatabase();
    }

/*     @GetMapping("/games/null")
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok(this.service.findAll());
    } */

    /* http://localhost:8080/api/games?page=2 */
    @GetMapping("/games")
    public ResponseEntity<Page<Game>> getAllPage(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
         Page<Game> gamePage = this.service.findAll(pageable);
        return ResponseEntity.ok(gamePage);
    }
    
    
    @GetMapping("/games/trending")
    public ResponseEntity<List<Game>> getTrendingGames(){
        return ResponseEntity.ok(this.trendingService.findAll());
    }

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