package com.kairosgames.kairos_games.repository;

import com.kairosgames.kairos_games.model.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RolEntity, Long> {

    List<RolEntity> findRoleEntitiesByRolIn(List<String> roleNames);
}
