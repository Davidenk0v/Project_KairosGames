package com.kairosgames.kairos_games.service;

import com.kairosgames.kairos_games.TrendingGameScrapper;
import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.model.Trending;
import com.kairosgames.kairos_games.repository.GameRepository;
import com.kairosgames.kairos_games.repository.TrendingRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class TrendingService {

    private TrendingRepository trendingRepository;
    private GameRepository gameRepository;
    private TrendingGameScrapper scrapper;

    public TrendingService(TrendingRepository trendingRepository, GameRepository gameRepository, TrendingGameScrapper scrapper){
        this.trendingRepository = trendingRepository;
        this.gameRepository = gameRepository;
        this.scrapper = scrapper;
    }
    public Set<String> loadDatabase(){
        try {
            Set<String> gamesNames = scrapper.getAllTrending();
            for (String gameName : gamesNames){
                List<Game> games = gameRepository.findByName(gameName);
                if (!games.isEmpty()) {
                    Game game = games.get(0);
                    Trending trending = new Trending();
                    trending.setGame(game);
                    trending.setId(game.getId());
                    trendingRepository.save(trending);
                }
            }
            return gamesNames;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}