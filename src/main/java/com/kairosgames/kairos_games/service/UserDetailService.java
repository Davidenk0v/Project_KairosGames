package com.kairosgames.kairos_games.service;

import java.util.List;
import java.util.Optional;

import com.kairosgames.kairos_games.model.UserEntity;

public interface UserDetailService {



// Metodos de consulta

    // RETRIEVE
    List<UserEntity> findAll();

    UserEntity findByUsername(String username);

    Optional<UserEntity> findById(Long id);

    // UPDATE
    UserEntity update(Long id, UserEntity game);

    // CREATE
    UserEntity save(UserEntity game);

    // DELETE
    void deleteById(Long id);

    void deleteAll();

    //Metodos de relacion entre tablas
    void addGameToPreference(Long user_id, Long game_id);
    
} 
