package com.kairosgames.kairos_games.controller;

import com.kairosgames.kairos_games.model.Preferences;
import com.kairosgames.kairos_games.model.UserPreferenceRequest;
import com.kairosgames.kairos_games.service.UserDetailService;
import com.kairosgames.kairos_games.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class PreferencesController {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private UserPreferenceService userPreferenceService;


    @GetMapping("/preferences")
    public ResponseEntity<List<Preferences>> getAllPreferences(){
        return ResponseEntity.ok(this.userDetailService.allPreferences());
    }

//    @PostMapping("/preferences/add/{user_id}")
//    public ResponseEntity<String> addPreferencesToUser(@PathVariable("user_id") Long userId, @RequestBody List<UserPreferenceRequest> preferences) {
//        this.service.addPreferenceToUser(userId, preferences);
//
//        return ResponseEntity.ok("Añadidas todas las preferencias");
//    }

    // @PostMapping("/preferences/add/{user_id}")
    // public ResponseEntity<String> addPreferencesToUser(@PathVariable("user_id") Long userId, @RequestBody List<UserPreferenceRequest> preferences) {
    //     userPreferenceService.saveAllPreferences(userId, preferences);
    //     return ResponseEntity.ok("Añadidas todas las preferencias");
    // }

//    @PostMapping("/preferences/remove/{user_id}/{preference_id}")
//    public ResponseEntity<String> removePreferencesToUser(@RequestBody Long user_id, @RequestBody Long game_id) {
//        this.userDetailService.removePreferenceToUser(user_id, game_id);
//
//        return ResponseEntity.ok("Borrado");
//    }



    @PostMapping("/preferences/add/{user_id}")
    public ResponseEntity<String> addPreferencesToUser(@PathVariable("user_id") Long userId, @RequestBody List<UserPreferenceRequest> preferences) {
        userPreferenceService.saveAllPreferences(userId, preferences);
        return ResponseEntity.ok("Añadidas todas las preferencias");
    }
}
