package com.kairosgames.kairos_games.controller;


import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.service.UserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id){
        return ResponseEntity.ok(this.service.findById(id).get());
    }

    @GetMapping("/users/name/{name}")
    public ResponseEntity<UserEntity> findByUserName(@PathVariable String username){
        return ResponseEntity.ok(this.service.findByUsername(username));
    }

    @PostMapping("/users/games/add/{user_id}/{game_id}")
    public ResponseEntity<String> addGameToList(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.addGameToList(user_id, game_id);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Añadido");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@RequestParam String id){
        this.service.deleteById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User eliminated");
    }
} 

