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

    @GetMapping("/users/name/{username}")
    public ResponseEntity<UserEntity> findByUserName(@PathVariable String username){
        return ResponseEntity.ok(this.service.findByUsername(username));
    }

    @GetMapping("/user/games/add/{userId}/{gameId}")
    public ResponseEntity<String> addGameToList(@PathVariable Long userId, @PathVariable Long gameId) {
        try{
            this.service.addGameToList(userId, gameId);
            return ResponseEntity.status(HttpStatus.OK).body("Añadido");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("No se pudo añadir");
        }

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserEntity newData, @PathVariable Long id){
        this.service.update(id, newData);
        return ResponseEntity.status(HttpStatus.OK).body("Editado");
    }

    @DeleteMapping("/user/games/{userId}/{gameId}")
    public ResponseEntity<String> removeGameToList(@PathVariable Long userId, @PathVariable Long gameId) {
        this.service.removeGameToList(userId, gameId);

        return ResponseEntity.status(HttpStatus.OK).body("Eliminado");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        this.service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User eliminated");
    }
} 

