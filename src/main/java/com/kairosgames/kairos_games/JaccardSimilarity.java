package com.kairosgames.kairos_games;

import com.kairosgames.kairos_games.model.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JaccardSimilarity {
    private static double numberJaccard = 0.98;
    private static final Set<String> keywords = new HashSet<>(Set.of(
            "PC", "Steam", "Key", "GLOBAL", "Deluxe", "Europe", "Xbox", "One", "Live", "PlayStation", "Nintendo",
            "Switch", "VR", "MAC", "OS", "Uplay", "Origin", "Store", "Code", "Código", "Battle.net", "Clave", "Epic", "Games",
            "Connect", "Series", "Ultimate", "Standard", "NORTHEAST", "AMERICA", "ROW", "MULTI", "Multi",
            "Platform", "Network", "EUROPE", "Pase", "anual", "Lote", "Bundle", "Pack", "Season", "Pass", "Supporter",
            "Premium", "Collector's", "Exclusive", "Content", "Creator", "Early", "Access", "Pre-order", "Bonus",
            "Complete", "Starter", "Supreme", "Legacy", "Rampage", "Incl.", "Digital", "X", "S", "Online", "Management",
            "Experience", "Event", "Pre", "X|S", "Mog", "Mog Station", "Marketplace", "Collection", "Black", "Regional",
            "Express", "Frontline", "Cross-Gen", "Redemption", "Member", "Selling", "Sale", "Retail", "Public", "Special",
            "Plus", "Media", "Director's", "Prequel", "LIVE", "Test", "Marketing", "Double", "Triple", "Diamond",
            "Square", "Featured", "LUXE", "Platinum", "Royal", "Elite", "Offline"
    ));

    public static List<Game> jaccardSimilarity(String title, List<Game> games){
        Set<String> titleWithoutNoise = removeNoise(title);
        List<Game> gamesWithSimilarTitles = new ArrayList<>();

        Set<String> addedPlatforms = new HashSet<>();
        for (Game game : games){
            if (!addedPlatforms.contains((game.getPlatform()))){
                Set<String> gameWithoutNoise = removeNoise(game.getName());
                double similarity = calculateJaccardSimilarity(titleWithoutNoise, gameWithoutNoise);
                if (similarity >= numberJaccard){
                    gamesWithSimilarTitles.add(game);
                    addedPlatforms.add(game.getPlatform());
                }
            }
        }
        return gamesWithSimilarTitles;
    }

    private static Set<String> removeNoise(String title){
        Set<String> titleWithoutNoise = new HashSet<>();
        String[] words = title.split("[\\s\\-\\[\\]()|]+");
        for (String word : words){
            if (!keywords.contains(word)){
                titleWithoutNoise.add(word);
            }
        }
        return titleWithoutNoise;
    }

    private static double calculateJaccardSimilarity(Set<String> title, Set<String> otherTitle){
        int intersectionSize = intersectionSize(title, otherTitle);
        int unionSize = unionSize(title, otherTitle);
        return (double) intersectionSize / unionSize;
    }

    private static int intersectionSize(Set<String> title, Set<String> otherTitle){
        Set<String> intersection = new HashSet<>(title);
        intersection.retainAll(otherTitle);
        return intersection.size();
    }
    private static int unionSize(Set<String> title, Set<String> otherTitle){
        Set<String> union = new HashSet<>(title);
        union.addAll(otherTitle);
        return union.size();
    }

}
