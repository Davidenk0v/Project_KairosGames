package com.kairosgames.kairos_games.repository;

import com.kairosgames.kairos_games.model.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferenceRepository extends JpaRepository<UserPreferences, Long> {

}
