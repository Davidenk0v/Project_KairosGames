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
    private String actual_price;
    private String low_price;
    private String high_price;

    public Game() {
    }

    public Game(Long id, String name, String actual_price) {
        this.id = id;
        this.name = name;
        this.actual_price = actual_price;
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

    public String getActualPrice() {
        return actual_price;
    }

    public void setActualPrice(String actual_price) {
        this.actual_price = actual_price;
    }

    public String getLowPrice() {
        return low_price;
    }

    public void setLowPrice(String newPrice) {
        // this.low_price = newPrice < this.low_price ? newPrice : this.low_price;
    }

    public String getHighPrice() {
        return high_price;
    }

    public void setHighPrice(String actual_price) {
        // this.high_price = newPrice > this.high_price ? newPrice : this.high_price;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", actual_price=" + actual_price +
                ", low_price="+ low_price +
                ", high_price="+ high_price +
                '}';
    }
}