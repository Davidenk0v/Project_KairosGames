package com.kairosgames.kairos_games;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import com.kairosgames.kairos_games.model.Game;
import com.kairosgames.kairos_games.model.UserEntity;
import com.kairosgames.kairos_games.service.UserDetailService;
import com.kairosgames.kairos_games.service.UserDetailsServiceImpl;

@SpringBootApplication
public class KairosGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KairosGamesApplication.class, args);
	}

}
