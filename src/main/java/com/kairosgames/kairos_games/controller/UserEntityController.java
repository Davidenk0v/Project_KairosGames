package com.kairosgames.kairos_games.controller;


import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.service.UserDetailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserEntityController {

    @Autowired
    private UserDetailService service;

    private static final Logger logger = LoggerFactory.getLogger(UserEntityController.class);

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll());
    }

    @GetMapping("/user/games/{id}")
    public ResponseEntity<Set<Game>> getUserGames(@PathVariable Long id){
        return ResponseEntity.ok(this.service.getUserGames(id));
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id){
        return ResponseEntity.ok(this.service.findById(id).get());
    }

    @GetMapping("/users/name/{name}")
    public ResponseEntity<UserEntity> findByUserName(@PathVariable String username){
        return ResponseEntity.ok(this.service.findByUsername(username));
    }

    @PostMapping("/user/games/add/{user_id}/{game_id}")
    public ResponseEntity<String> addGameToList(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.addGameToList(user_id, game_id);

        return ResponseEntity.status(HttpStatus.OK).body("Añadido");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserEntity newData, @PathVariable Long id){
        this.service.update(id, newData);
        return ResponseEntity.status(HttpStatus.OK).body("Editado");
    }

    @DeleteMapping("/user/games/{user_id}/{game_id}")
    public ResponseEntity<String> removeGameToList(@PathVariable Long user_id, @PathVariable Long game_id) {
        this.service.removeGameToList(user_id, game_id);

        return ResponseEntity.status(HttpStatus.OK).body("Eliminado");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        this.service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User eliminated");
    }
} 

