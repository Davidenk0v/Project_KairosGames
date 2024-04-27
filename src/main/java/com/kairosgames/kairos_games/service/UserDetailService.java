package com.kairosgames.kairos_games.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.model.Preferences;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.model.UserPreferenceRequest;


public interface UserDetailService {



// Metodos de consulta

    // RETRIEVE
    List<UserEntity> findAll();

    UserEntity findByUsername(String username);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    // UPDATE
    UserEntity update(Long id, UserEntity game);

    // CREATE
    UserEntity save(UserEntity game);

    // DELETE
    void deleteById(Long id);

    void deleteAll();

    public Set<Game> getUserGames(Long id);

    //Metodos de relacion usuario ta
    void addGameToList(Long user_id, Long game_id);

    List<Preferences> allPreferences();
    void removeGameToList(Long user_id, Long game_id);

//    void addPreferenceToUser(Long user_id, List<UserPreferenceRequest> preference);
    void removePreferenceToUser(Long user_id, Long preference_id);
    
} 
