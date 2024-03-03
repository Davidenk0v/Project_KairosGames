package com.kairosgames.kairos_games;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.service.GameService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameScrapper
{

    public GameScrapper(){
    }

    public List<Game> getG2AGamesUrl(){
        final String URL = "https://www.g2a.com/category/gaming-c1";
        List<Game> gamesList = new ArrayList<>();
        try{
            Document document = Jsoup.connect(URL).get();
            Elements games = document.select("section li");
            for (Element game: games){
                String title = game.select("div > h3").text();
                String price = game.select("li span[data-locator]").text();
                if(!title.isEmpty() || !price.isEmpty()){
                    gamesList.add(new Game(null, title, new BigDecimal(price)));
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return gamesList;
    }

        private List<String> getInstaGamingGamesURL(){
            final String URL = "https://www.instant-gaming.com/es/busquedas/?platform%5B%5D=1&type%5B%5D=&sort_by=bestsellers_desc&min_reviewsavg=10&max_reviewsavg=100&noreviews=1&min_price=0&max_price=200&noprice=1&instock=1&gametype=games&search_tags=0&query=";
            List<String> urlList = new ArrayList<>();
            try{
                Document document = Jsoup.connect(URL).get();
                Elements games = document.select(".item");

                for (Element game: games){
                    String link = game.select("a").attr("href");
                    urlList.add(link);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            return urlList;
        }

        public List<Game> getInstaGames(){
            List<String> urlsList = getInstaGamingGamesURL();
            List<Game> gamesList = new ArrayList<>();
            try{
                for(String url: urlsList){
                    Document html = Jsoup.connect(url).get();
                    Element data = html.selectFirst(".data");
                    String name = data.select(".game-title").text();
                    String actualPrice = data.select(".total").text();
                    actualPrice = actualPrice.substring(0, actualPrice.length() -1 );
                    String urlImg = data.select("picture > img").attr("data-src");
                        gamesList.add(new Game(name, urlImg, new BigDecimal(actualPrice)));
                }
            }catch(IOException e){
                JSONObject errorJson = new JSONObject();
                errorJson.put("error", e.getMessage());
            }
            return gamesList;
        }

}