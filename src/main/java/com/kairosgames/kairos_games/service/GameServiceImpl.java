package com.kairosgames.kairos_games.service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kairosgames.kairos_games.GameScrapper;
import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.repository.GameRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository repository;
    private GameScrapper scrapper;

    public GameServiceImpl(GameRepository repository, GameScrapper scrapper) {
        this.repository = repository;
        this.scrapper = scrapper;
    }

    @Override
    public List<Game> loadDatabase() {
        return this.scrapper.getEnebaGames();
    }

    @Override
    public List<Game> findAll() {
        return this.repository.findAll();
    }

    @Override
    public ResponseEntity<List<Game>> prueba() {
        List<Game> games = scrapper.getEnebaGames();
        if (games.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(games);
    }

    @Override
    public List<Game> findByname(String name) {
        Objects.requireNonNull(name);
        return this.repository.findByName(name);
    }

    @Override
    public Optional<Game> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Game save(Game game) {
        this.repository.save(game);
        return game;
    }

    @Override
    public Game update(Long id, Game game) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        this.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

}