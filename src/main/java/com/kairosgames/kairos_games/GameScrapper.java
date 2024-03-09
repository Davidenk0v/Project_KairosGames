package com.kairosgames.kairos_games;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.util.PropertySource.Comparator;
import org.hibernate.mapping.Collection;
import org.json.JSONObject;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.service.GameService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

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

    public List<String> getG2aURL() {
        List<String> urlList = new ArrayList<>();
        // for (int i = 1; i <= 5; i++) {
        final String URL = "https://www.g2a.com/category/gaming-c1";
        try {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36";
            Document document = Jsoup.connect(URL).userAgent(userAgent).get();
            logger.error("HTML:", document);
            Elements games = document
                    .select("h3");
            logger.error("Cositas: " + games);
            for (Element game : games) {
                String link = game.attr("id");
                if (true) {
                    urlList.add(link);
                }
                logger.error("Cositas 2: " + link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // }
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
                    Element elementPage = game.select("a.oSVLlh").first();
                    String urlPage = "https://www.eneba.com" + elementPage.attr("href");
                    Element high_priceElement = game.select("div.iqjN1x span.L5ErLT").first();

                    String platform = getPlataformEneba(urlPage);

                    if (high_priceElement == null) {
                        gamesList.add(new Game(null, title, new BigDecimal(price), urlImg, urlPage, platform, "Eneba"));
                    } else {
                        String high_price = high_priceElement.text();
                        high_price = (high_price.substring(0, high_price.length() - 1).replace(",", ".")).trim();
                        gamesList.add(
                                new Game(title, new BigDecimal(price), urlImg, new BigDecimal(high_price),
                                        urlPage, platform, "Eneba"));
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gamesList;
    }

    private String getPlataformEneba(String urlPage) {
        String platform = "";
        try {
            Document document = Jsoup.connect(urlPage).get();
            platform = document.select("ul.oBo9oN li").text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return platform;
    }

    public List<Game> getAllGames() {
        List<Game> allGames = new ArrayList<>(getInstaGames());
        allGames.addAll(getEnebaGames());
        Collections.sort(allGames, Game.CompareName);
        return allGames;
    }
}