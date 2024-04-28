package com.kairosgames.kairos_games.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto{

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private Integer edad;

    @JsonAlias("preferences")
    private List<String> preferences;

}
