package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trending")
public class Trending {

    @Id
    private Long id;

    @OneToOne()
    @JoinColumn(name = "game_id")
    private Game game;



}
