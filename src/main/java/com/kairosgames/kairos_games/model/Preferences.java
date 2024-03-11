package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "preferences")
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "preferences")
    Set<UserEntity> users;
}
