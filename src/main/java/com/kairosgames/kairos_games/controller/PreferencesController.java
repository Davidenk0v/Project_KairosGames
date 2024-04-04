package com.kairosgames.kairos_games.controller;

import com.kairosgames.kairos_games.model.Preferences;
import com.kairosgames.kairos_games.service.UserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class PreferencesController {

    @Autowired
    private UserDetailService service;


    @GetMapping("/preferences")
    public ResponseEntity<List<Preferences>> getAllPreferences(){
        return ResponseEntity.ok(this.service.allPreferences());
    }

    @PostMapping("/preferences/add/{user_id}/{preference_id}")
    public ResponseEntity<String> addGameToUser(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.addPreferenceToUser(user_id, game_id);
        
        return ResponseEntity.ok("Añadido");
    }

    @PostMapping("/preferences/remove/{user_id}/{preference_id}")
    public ResponseEntity<String> removeGameToUser(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.removePreferenceToUser(user_id, game_id);
        
        return ResponseEntity.ok("Borrado");
    }



}
