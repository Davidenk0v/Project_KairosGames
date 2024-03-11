package com.kairosgames.kairos_games.service;

import com.kairosgames.kairos_games.DestacadosGameScrapper;
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
    private DestacadosGameScrapper destacados;

    public GameServiceImpl(GameRepository repository, GameScrapper scrapper, DestacadosGameScrapper destacados) {
        this.repository = repository;
        this.scrapper = scrapper;
        this.destacados = destacados;
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
    public List<String> prueba() {
        return this.destacados.getDestacadosG2a();
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