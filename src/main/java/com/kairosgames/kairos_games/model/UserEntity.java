package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    
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

    private boolean isAccountNonExpired;


    private boolean isAccountNonLocked;


    private boolean isCredentialsNonExpired;


    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    @Enumerated(EnumType.STRING)
    private Set<RolEntity> roles;


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

    public Set<RolEntity> getRoles() {
        return roles;
    }

    public void setRol(Set<RolEntity> roles) {
        this.roles = roles;
    }

    public void setPreferences(Preferences preference) {
        this.preferences.add(preference);
    }

    public Set<Game> getUser_games() {
        return user_games;
    }

    public void setUser_games(Game user_games) {
        this.user_games.add(user_games);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
