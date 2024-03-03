package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name= "actual_price")
    private BigDecimal actualPrice;

    @Column(name= "lower_price")
    private BigDecimal lowPrice = new BigDecimal(0);

    @Column(name= "higher_price")
    private BigDecimal highPrice = new BigDecimal(0);

    @Column(name= "url_img")
    private String urlImg;

    public Game() {
    }

    public Game(Long id, String name, BigDecimal actualPrice, String urlImg) {
        this.id = id;
        this.name = name;
        this.setActualPrice(actualPrice);
        this.urlImg = urlImg;
    }

    public Game(String name, String urlImg, BigDecimal actualPrice) {
        this.name = name;
        this.actualPrice = actualPrice;
        this.urlImg = urlImg;
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

    public String getUrl() {
        return urlImg;
    }

    public void setUrl(String urlImg) {
        this.urlImg = urlImg;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    //Al settear el precio compara si es el mayor o menor precio que ha tenido el juego
    public void setActualPrice(BigDecimal actualPrice) {
        setLowPrice(actualPrice);
        setHighPrice(actualPrice);
        this.actualPrice = actualPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal newPrice) {
        if(newPrice.compareTo(this.lowPrice) < 0 ){
            this.lowPrice = newPrice;
        }
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal newPrice) {
        if (newPrice.compareTo(this.highPrice) > 0) {
            this.highPrice = newPrice;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", actualPrice=" + actualPrice +
                ", lowPrice="+ lowPrice +
                ", highPrice="+ highPrice +
                ", url_img="+ urlImg +
                '}';
    }
}