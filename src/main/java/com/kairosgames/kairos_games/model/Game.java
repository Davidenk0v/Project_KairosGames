package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "games")
public class Game {

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

    public Game() {
    }

    public Game(Long id, String name, BigDecimal actualPrice, String urlImg, String urlPage, String platform,
                String shop) {
        this.id = id;
        this.name = name;
        this.setActualPrice(actualPrice);
        this.urlImg = urlImg;
        this.urlPage = urlPage;
        this.platform = platform;
        this.shop = shop;
    }

    public Game(Long id, String name, BigDecimal actualPrice, String urlImg, BigDecimal high_price, String urlPage,
                String platform ,String shop) {
        this.id = id;
        this.name = name;
        this.urlImg = urlImg;
        this.higherPrice = high_price;
        this.setActualPrice(actualPrice);
        this.urlPage = urlPage;
        this.platform = platform;
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
}