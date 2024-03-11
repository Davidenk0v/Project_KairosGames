package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trending")
public class Trending {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId
    @JoinColumn(name = "game_id")
    private Game game;

    public Trending(){}

    public Trending(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
