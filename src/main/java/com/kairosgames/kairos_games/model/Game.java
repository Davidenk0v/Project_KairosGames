package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal actual_price;
    private BigDecimal low_price = new BigDecimal(0);
    private BigDecimal high_price = new BigDecimal(0);
    private String urlImg;

    public Game() {
    }

    public Game(Long id, String name, BigDecimal actual_price, String urlImg) {
        this.id = id;
        this.name = name;
        this.actual_price = actual_price;
        this.urlImg = urlImg;
        setLowPrice(actual_price);
        setHighPrice(actual_price);
    }

    public Game(String name, String urlImg, BigDecimal actual_price) {
        this.name = name;
        this.actual_price = actual_price;
        this.urlImg = urlImg;
        setLowPrice(actual_price);
        setHighPrice(actual_price);
    }

    public Game(Long id, String name, BigDecimal actual_price, String urlImg, BigDecimal high_price){
        this.id = id;
        this.name = name;
        this.actual_price = actual_price;
        this.urlImg = urlImg;
        this.high_price = high_price;
        setLowPrice(actual_price);
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
        return actual_price;
    }

    public void setActualPrice(BigDecimal actual_price) {
        this.actual_price = actual_price;
    }

    public BigDecimal getLowPrice() {
        return low_price;
    }

    public void setLowPrice(BigDecimal newPrice) {
        int result = this.low_price.compareTo(newPrice);
        if(result < 0 ){
            this.low_price = newPrice;
        }
    }

    public BigDecimal getHighPrice() {
        return high_price;
    }

    public void setHighPrice(BigDecimal newPrice) {
        int result = this.high_price.compareTo(newPrice);
        if(result < 0 ){
            this.high_price = newPrice;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", actual_price=" + actual_price +
                ", low_price="+ low_price +
                ", high_price="+ high_price +
                ", url_img="+ urlImg +
                '}';
    }
}