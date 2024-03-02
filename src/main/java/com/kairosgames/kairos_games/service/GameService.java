package com.kairosgames.kairos_games.service;


import java.util.List;
import java.util.Optional;

import com.kairosgames.kairos_games.model.Game;

public interface GameService {

    //Metodos de consulta

    //RETRIEVE
    List<Game> findAll();
    List<Game> findByname(String name);

    Optional<Game> findById(Long id);

    //CREATE
    Game save(Game game);

    //DELETE
    void deleteById(Long id);
    void deleteAll();
}