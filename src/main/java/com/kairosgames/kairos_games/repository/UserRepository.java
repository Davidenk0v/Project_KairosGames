package com.kairosgames.kairos_games.repository;

import com.kairosgames.kairos_games.model.UserEntity;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);
<<<<<<< HEAD
  
=======


>>>>>>> 6347fca3f17f3e2ddf7558140ab799a2d936d342
}
