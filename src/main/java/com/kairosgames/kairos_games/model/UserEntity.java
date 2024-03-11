package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    private String password;

    @NotNull
    @Max(99)
    private Integer edad;

    @Enumerated(EnumType.STRING)
    private ERole rol;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_preferences", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "preferences_id"))
    private Set<Preferences> preferences;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_game", 
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> user_games = new HashSet<>();

    public ERole getRol() {
        return rol;
    }

    public void setRol(ERole rol) {
        this.rol = rol;
    }

    public void setPreferences(Set<Preferences> preferences) {
        this.preferences = preferences;
    }

    public Set<Game> getUser_games() {
        return user_games;
    }

    public void setUser_games(Game user_games) {
        this.user_games.add(user_games);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Set<Preferences> getPreferences(){
        return this.preferences;
    }

    public void setPreferences(Preferences preferences){
        this.preferences.add(preferences);
    }

}
