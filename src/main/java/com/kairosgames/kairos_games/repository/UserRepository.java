package com.kairosgames.kairos_games.repository;

import com.kairosgames.kairos_games.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    List<UserEntity> findByUsername(@Param("username") String username);

    @Query("INSERT INTO user_game (user_id, game_id) VALUES (?1, ?2)")
    void addGameToUserList(Long user_id, Long game_id);
}
