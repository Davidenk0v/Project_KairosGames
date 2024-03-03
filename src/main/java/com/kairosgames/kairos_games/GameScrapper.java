package com.kairosgames.kairos_games;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kairosgames.kairos_games.model.Game;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameScrapper
{
    private final Logger logger = LoggerFactory.getLogger(GameScrapper.class);

    public GameScrapper(){

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
                        String price = game.select("span.DTv7Ag span.L5ErLT").text();
                        price = (price.substring(0, price.length() - 1).replace(",", ".")).trim();
                        Element imgElement = game.select("img").first();
                        String urlImg = imgElement.attr("src");
                        Element high_priceElement = game.select("div.iqjN1x span.L5ErLT").first();

                        if(high_priceElement == null){
                            gamesList.add(new Game(null, title, new BigDecimal(price), urlImg));
                        }else{
                            String high_price = high_priceElement.text();
                            high_price = (high_price.substring(0, high_price.length() - 1).replace(",", ".")).trim();
                            gamesList.add(new Game(null, title, new BigDecimal(price), urlImg, new BigDecimal(high_price)));
                        }

                    }
                }

            }catch(IOException e){
                e.printStackTrace();
            }
            return gamesList;
        }

        public List<Game> getAll(){
            //List<Game> allGames = new ArrayList<>(getInstaGamingGames());
            //allGames.addAll(getG2AGames());
            //allGames.addAll(getEnebaGames());
            List<Game> allGames = new ArrayList<>(getEnebaGames());
            return allGames;
        }
}