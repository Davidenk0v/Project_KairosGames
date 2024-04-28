package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_preference")
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user_id;

    @ManyToOne
    @JoinColumn(name = "preference_id")
    private Preferences preference_id;

    @Column(name = "response")
    private String response;

    public Long getUserId(){return this.user_id.getId();}
    public void setUserId(UserEntity userId){this.user_id = userId;}
    public Preferences getPreferenceId(){return this.preference_id;}
    public void setPreferenceId(Preferences preferenceId){this.preference_id = preferenceId;}
    public String getResponse(){return this.response;}
    public void setResponse(String response){this.response = response;}
}
