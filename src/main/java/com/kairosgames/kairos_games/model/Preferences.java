package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("users")
@Entity
@Table(name = "preferences")
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

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
}
