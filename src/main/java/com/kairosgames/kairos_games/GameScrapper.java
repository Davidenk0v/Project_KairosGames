package com.kairosgames.kairos_games;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.kairosgames.kairos_games.model.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameScrapper
{
    public GameScrapper(){

    }

    public List<Game> getG2AGames(){
        String url = "https://www.g2a.com/es/category/games-c189";
        List<Game> gamesList = new ArrayList<>();
        try{
            Document document = Jsoup.connect(url).get();
            Elements games = document.select("section li");

            for (Element game: games){
                String title = game.select("div > h3").text();
                String price = game.select("li span[data-locator]").text();
                if(!title.isEmpty() || !price.isEmpty()){
                    gamesList.add(new Game(null, title, price));
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return gamesList;
    }

        public List<Game> getInstaGamingGames(){
            String url = "https://www.instant-gaming.com/es/mas-vendidos/";
            List<Game> gamesList = new ArrayList<>();
            try{
                Document document = Jsoup.connect(url).get();
                Elements games = document.select(".item");

                for (Element game: games){
                    String title = game.select(".name").text();
                    String price = game.select(".price").text();

                    gamesList.add(new Game(null, title, price));
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            return gamesList;
        }

        public List<Game> getEnebaGames(){

            List<Game> gamesList = new ArrayList<>();
            try{
                for(int i = 1; i <= 50; i ++)
                {
                    String url = "https://www.eneba.com/es/store/games?page="+i;
                    Document document = Jsoup.connect(url).get();
                    Elements games = document.select(".uy1qit");
                    for (Element game : games){
                        String title = game.select(".YLosEl").text();
                        String price = game.select(".L5ErLT").text();

                        gamesList.add(new Game(null, title, price));
                    }
                }

            }catch(IOException e){
                e.printStackTrace();
            }
            return gamesList;
        }

        public List<Game> getAll(){
            List<Game> allGames = new ArrayList<>(getInstaGamingGames());
            allGames.addAll(getG2AGames());
            allGames.addAll(getEnebaGames());
            return allGames;
        }
}