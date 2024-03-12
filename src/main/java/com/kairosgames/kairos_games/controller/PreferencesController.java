package com.kairosgames.kairos_games.controller;

import com.kairosgames.kairos_games.service.PreferencesServiceImpl;
import com.kairosgames.kairos_games.service.UserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PreferencesController {

    @Autowired
    private UserDetailService service;

    

    @PostMapping("/preferences/add/{user_id}/{preference_id}")
    public ResponseEntity addGameToUser(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.addPreferenceToUser(user_id, game_id);
        
        return ResponseEntity.ok("Añadido");
    }

    @PostMapping("/preferences/remove/{user_id}/{preference_id}")
    public ResponseEntity removeGameToUser(@RequestBody Long user_id, @RequestBody Long game_id) {
        this.service.removePreferenceToUser(user_id, game_id);
        
        return ResponseEntity.ok("Borrado");
    }



}
