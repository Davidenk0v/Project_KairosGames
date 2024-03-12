package com.kairosgames.kairos_games.repository;

import com.kairosgames.kairos_games.model.Trending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendingRepository extends JpaRepository<Trending, Long> {



}