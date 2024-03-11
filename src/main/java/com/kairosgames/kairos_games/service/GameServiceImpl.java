package com.kairosgames.kairos_games.service;

import com.kairosgames.kairos_games.DestacadosGameScrapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kairosgames.kairos_games.GameScrapper;
import com.kairosgames.kairos_games.exceptions.GameBadRequestException;
import com.kairosgames.kairos_games.exceptions.GameNotFoundException;
import com.kairosgames.kairos_games.exceptions.InternalServerErrorException;
import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.repository.GameRepository;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
        try{
            List<Game> gameList = this.scrapper.getAllGames();
            for (Game game : gameList){
                List<Game> sameName = repository.findByName(game.getName());
                if (sameName.isEmpty()) {
                    this.repository.save(game); // Si no encuentra ninguno juego con ese nombre lo añade a la base de datos
                } else {
                    for (Game game2 : sameName) {
                        if (game2.getPlatform().equals(game.getPlatform())) {
                            this.repository.save(game);
                        } else {
                            game2.setActualPrice(game.getActualPrice()); // Si encuentra alguno le actualiza el precio
                            this.repository.save(game2);
                        }
                    }
                }
            }
            return gameList;
        }catch(DataAccessException e){
            throw new InternalServerErrorException("Error updating database", e);
        }
    }

    @Override
    public List<Game> findAll() {
        if(this.repository.findAll().isEmpty()){
            throw new GameNotFoundException("No games found in database");
        }
        return this.repository.findAll();
    }

    @Override
    public List<Game> findByname(String name) {
        Objects.requireNonNull(name);
                if (this.repository.findByName(name).isEmpty()){
            throw new GameNotFoundException("Requested Game does not exist");
        }
        return this.repository.findByName(name);
    }

    @Override
    public Optional<Game> findById(Long id) {
        Objects.requireNonNull(id);
        if(id <= 0){
            throw new GameBadRequestException("the number must be greater than zero");
        }    
            Optional<Game> optional = this.repository.findById(id);
            if(!optional.isPresent()){
                throw new GameNotFoundException("Requested Game with id "+ id +" does not exist");
            }
            return this.repository.findById(id);
    }

    @Override
    public Game save(Game game) {
        Objects.requireNonNull(game);
        if(game.getId() != null){
            throw new GameBadRequestException("There is already a game with this id");
        }
        this.repository.save(game);
        return game;
    }

    @Override
    public Game update(Long id, Game game) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id);
        if(this.repository.findById(id).isEmpty()){
            throw new GameBadRequestException("Requested Game with id "+ id +" does not exist");
        }
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

}