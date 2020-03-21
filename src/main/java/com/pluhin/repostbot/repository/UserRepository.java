package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity getFirstByUsername(String username);
}
