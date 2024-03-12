package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("game_users")
@Entity
@Table(name = "games")
public class Game implements Comparator<Game> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url_img")
    private String urlImg;


    @Column(name = "actual_price")
    private BigDecimal actualPrice;

    @Column(name = "higher_price")
    private BigDecimal higherPrice = new BigDecimal(0);

    @Column(name = "lower_price")
    private BigDecimal lowerPrice = new BigDecimal(0);

    @Column(name = "url_page")
    private String urlPage;

    @Column(name = "platform")
    private String platform;

    @Column(name = "shop")
    private String shop;

    @ManyToMany(mappedBy = "user_games", fetch = FetchType.LAZY)
    private Set<UserEntity> game_users = new HashSet<>();

    public BigDecimal getHigherPrice() {
        return higherPrice;
    }

    public void setHigherPrice(BigDecimal higherPrice) {
        this.higherPrice = higherPrice;
    }

    public BigDecimal getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(BigDecimal lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Set<UserEntity> getGame_users() {
        return game_users;
    }

    public void setGame_users(Set<UserEntity> game_users) {
        this.game_users = game_users;
    }

    public Game() {
    }

    public Game(Long id, String name, BigDecimal actualPrice, String urlImg, String urlPage, String platform, String shop) {
        this.id = id;
        this.name = name;
        this.setActualPrice(actualPrice);
        this.urlImg = urlImg;
        this.urlPage = urlPage;
        this.platform = platform;
        this.shop = shop;
    }

    public Game(String name, BigDecimal actualPrice, String urlImg, BigDecimal highPrice, String platform,
            String urlPage, String shop) {
        this.name = name;
        this.urlImg = urlImg;
        this.higherPrice = highPrice;
        this.setActualPrice(actualPrice);
        this.platform = platform;
        this.urlPage = urlPage;
        this.shop = shop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    // Al settear el precio compara si es el mayor o menor precio que ha tenido el
    // juego
    public void setActualPrice(BigDecimal actualPrice) {
        setLowPrice(actualPrice);
        setHighPrice(actualPrice);
        this.actualPrice = actualPrice;
    }

    public BigDecimal getHighPrice() {
        return higherPrice;
    }

    public void setHighPrice(BigDecimal newPrice) {
        if (newPrice.compareTo(this.higherPrice) > 0) {
            this.higherPrice = newPrice;
        }
    }

    public BigDecimal getLowPrice() {
        return lowerPrice;
    }

    public void setLowPrice(BigDecimal newPrice) {
        if (newPrice.compareTo(this.lowerPrice) < 0 || lowerPrice.compareTo(BigDecimal.ZERO) == 0) {
            this.lowerPrice = newPrice;
        }
    }

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlataform(String plataform) {
        this.platform = plataform;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }


    public final static Comparator<Game> CompareName = new Comparator<Game>() {
        @Override
        public int compare(Game g1, Game g2) {
            return g1.getName().compareTo(g2.getName());
        }
    };

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", actualPrice=" + actualPrice +
                ", lowerPrice=" + lowerPrice +
                ", higherPrice=" + higherPrice +
                ", url_img=" + urlImg +
                ", url_page=" + urlPage +
                ", plataform=" + platform +
                ", shop=" + shop +
                '}';
    }



    @Override
    public int compare(Game o1, Game o2) {
        return 0;
    }
}