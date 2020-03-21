package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolesRepository extends JpaRepository<RoleEntity, Long> {

  @Query("FROM RoleEntity WHERE defaultRole = true")
  RoleEntity findDefault();
}
