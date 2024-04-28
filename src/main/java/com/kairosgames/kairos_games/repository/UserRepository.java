package com.kairosgames.kairos_games.repository;

import com.kairosgames.kairos_games.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername( String username);


    Optional<UserEntity> findByEmail(String email);

}
