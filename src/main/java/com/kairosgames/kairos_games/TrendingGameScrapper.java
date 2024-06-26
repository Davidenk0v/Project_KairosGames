package com.kairosgames.kairos_games;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class TrendingGameScrapper {


    public List<String> getTrendingG2a(){
        List<String> trendingG2a = new ArrayList<>();
        try{

            String url = "https://www.g2a.com/es/best-deals/trending-games";
            Document document = Jsoup.connect(url).get();
            Elements lista = document.select("section#0aed130e-80a3-497b-96fe-295e4829011a li");
            for (Element li : lista){
                String title = li.select("h3").text();
                trendingG2a.add(title);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return trendingG2a;
    }

    public List<String> getTrendingInstaGames(){
        List<String> trendingInstaGames = new ArrayList<>();
        try{
            String url = "https://www.instant-gaming.com/es/";
            Document document = Jsoup.connect(url).get();
            Elements games = document.select("div.products-trending .item");
            for (Element game : games){
                String title = game.select(".title").text();
                trendingInstaGames.add(title);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return trendingInstaGames;
    }

    public Set<String> getAllTrending(){
        Set<String> allTrending = new HashSet<>();
        allTrending.addAll(getTrendingG2a());
        allTrending.addAll(getTrendingInstaGames());
        return allTrending;
    }


}
