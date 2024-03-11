package com.kairosgames.kairos_games.repository;

import com.kairosgames.kairos_games.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferencesRepository  extends JpaRepository<Preferences, Long> {
    List<Preferences> findByName(String name);
}
