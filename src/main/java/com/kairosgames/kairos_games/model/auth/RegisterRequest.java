package com.kairosgames.kairos_games.model.auth;

import com.kairosgames.kairos_games.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Integer edad;
    private ERole role;

}
