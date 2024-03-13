package com.kairosgames.kairos_games.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.kairosgames.kairos_games.model.Game;

public interface GameService {

    // Metodos de consulta

    // RETRIEVE
    List<Game> findAll();
    Page<Game> findAll(Pageable pageable);

    List<Game> findByname(String name);

    Optional<Game> findById(Long id);

    // UPDATE
    Game update(Long id, Game game);

    // CREATE
    Game save(Game game);

    // DELETE
    void deleteById(Long id);

    void deleteAll();

    List<Game> loadDatabase();
}