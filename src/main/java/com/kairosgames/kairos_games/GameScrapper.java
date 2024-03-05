package com.kairosgames.kairos_games;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.service.GameService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameScrapper {
    private final Logger logger = LoggerFactory.getLogger(GameScrapper.class);

    public GameScrapper() {
    }

    private List<String> getInstaGamingGamesURL() {
        List<String> urlList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            final String URL = "https://www.instant-gaming.com/es/busquedas/?page_type=trendings&page=" + i;
            try {
                Document document = Jsoup.connect(URL).get();
                Elements games = document.select(".item");

                for (Element game : games) {
                    String link = game.select("a").attr("href");
                    urlList.add(link);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return urlList;
    }

    public List<Game> getInstaGames() {
        List<String> urlsList = getInstaGamingGamesURL();
        List<Game> gamesList = new ArrayList<>();
        try {
            for (String url : urlsList) {
                List<String> platformsList = new ArrayList<>();
                Document html = Jsoup.connect(url).get();
                Element data = html.selectFirst(".data");
                String name = data.select(".game-title").text();
                String actualPrice = data.select(".total").text();
                actualPrice = actualPrice.substring(0, actualPrice.length() - 1);
                String highPrice = data.select(".retail").text();
                highPrice = highPrice.substring(0, highPrice.length() - 1);
                platformsList.add(data.select("#platforms-choices > option").text());
                String platform = String.join(",", platformsList);
                String urlImg = data.select("picture > img").attr("data-src");
                gamesList.add(new Game(name, new BigDecimal(highPrice), urlImg, new BigDecimal(actualPrice), platform,
                        url, "InstaGaming"));
            }
        } catch (IOException e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", e.getMessage());
        }
        return gamesList;
    }

}