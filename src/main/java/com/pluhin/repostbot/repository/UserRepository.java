package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findFirstByToken(String token);

  List<UserEntity> findAllByRole(String role);

  void removeByUsername(String username);
}
