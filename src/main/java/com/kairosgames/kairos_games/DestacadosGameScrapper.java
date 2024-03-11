package com.kairosgames.kairos_games;

import com.kairosgames.kairos_games.model.Game;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DestacadosGameScrapper {

    public  DestacadosGameScrapper(){}

    private final Logger logger = LoggerFactory.getLogger(GameScrapper.class);

    public List<String> getDestacadosG2a(){
        List<String> destacadosG2a = new ArrayList<>();
        try{

            String url = "https://www.g2a.com/es/best-deals/trending-games";
            Document document = Jsoup.connect(url).get();
            Elements lista = document.select("section#0aed130e-80a3-497b-96fe-295e4829011a li");
            for (Element li : lista){
                String title = li.select("h3").text();
                destacadosG2a.add(title);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return destacadosG2a;
    }

    public List<String> getDestacadosInstaGames(){
        List<String> destacadosInstaGames = new ArrayList<>();
        try{
            String url = "https://www.instant-gaming.com/es/";
            Document document = Jsoup.connect(url).get();
            Elements games = document.select("div.products-trending .item");
            for (Element game : games){
                String title = game.select(".title").text();
                destacadosInstaGames.add(title);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return destacadosInstaGames;
    }


}
