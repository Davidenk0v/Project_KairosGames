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

    public List<Game> getEnebaGames() {

        List<Game> gamesList = new ArrayList<>();
        try {
            for (int i = 1; i <= 5; i++) {
                String url = "https://www.eneba.com/es/store/games?page=" + i;
                Document document = Jsoup.connect(url).get();
                Elements games = document.select(".uy1qit");
                for (Element game : games) {
                    String title = game.select(".YLosEl").text();
                    String price = game.select("span.DTv7Ag span.L5ErLT").text();
                    price = (price.substring(0, price.length() - 1).replace(",", ".")).trim();
                    Element imgElement = game.select("img").first();
                    String urlImg = imgElement.attr("src");
                    Element high_priceElement = game.select("div.iqjN1x span.L5ErLT").first();

                    if (high_priceElement == null) {
                        gamesList.add(new Game(null, title, new BigDecimal(price), urlImg, "", "", ""));
                    } else {
                        String high_price = high_priceElement.text();
                        high_price = (high_price.substring(0, high_price.length() - 1).replace(",", ".")).trim();
                        gamesList.add(
                                new Game(null, title, new BigDecimal(price), urlImg, new BigDecimal(high_price), "", ""));
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gamesList;
    }

    private List<String> getInstaGamingGamesURL() {
        final String URL = "https://www.instant-gaming.com/es/busquedas/?platform%5B%5D=1&type%5B%5D=&sort_by=bestsellers_desc&min_reviewsavg=10&max_reviewsavg=100&noreviews=1&min_price=0&max_price=200&noprice=1&instock=1&gametype=games&search_tags=0&query=";
        List<String> urlList = new ArrayList<>();
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
        return urlList;
    }

    public List<Game> getInstaGames() {
        List<String> urlsList = getInstaGamingGamesURL();
        List<Game> gamesList = new ArrayList<>();
        try {
            for (String url : urlsList) {
                Document html = Jsoup.connect(url).get();
                Element data = html.selectFirst(".data");
                String name = data.select(".game-title").text();
                String actualPrice = data.select(".total").text();
                actualPrice = actualPrice.substring(0, actualPrice.length() - 1);
                String urlImg = data.select("picture > img").attr("data-src");
                gamesList.add(new Game(null, name, new BigDecimal(actualPrice), urlImg, "test", "test", "instGames"));
            }
        } catch (IOException e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", e.getMessage());
        }
        return gamesList;
    }

}