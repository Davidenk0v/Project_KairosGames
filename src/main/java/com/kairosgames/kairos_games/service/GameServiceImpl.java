package com.kairosgames.kairos_games.service;


import org.springframework.stereotype.Service;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.repository.GameRepository;

import io.micrometer.common.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository repository;

    public GameServiceImpl(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Game> findAll() {
        return this.repository.findAll();
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
    public void deleteById(Long id) {
        this.deleteById(id);
    }

    @Override
    public void deleteAll() {
    this.repository.deleteAll();
    }
}