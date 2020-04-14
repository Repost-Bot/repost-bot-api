package com.pluhin.repostbot.registration;

import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.model.user.BotRegistrationEntity;
import com.pluhin.repostbot.model.user.RoleDTO;
import com.pluhin.repostbot.repository.UserRepository;
import com.pluhin.util.registration.model.ConfirmationEntity;
import com.pluhin.util.registration.model.DefaultRegistrationEntity;
import com.pluhin.util.registration.model.RegistrationEntity;
import com.pluhin.util.registration.repository.RegistrationRepository;
import java.time.LocalDateTime;

public class DefaultRegistrationRepository implements RegistrationRepository {

  private final UserRepository userRepository;

  public DefaultRegistrationRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void save(RegistrationEntity entity) {
    BotRegistrationEntity botEntity = (BotRegistrationEntity) entity;
    UserEntity userEntity = new UserEntity();
    userEntity.setToken(entity.getRegistrationToken());
    userEntity.setFullName(entity.getFullName());
    userEntity.setRole(entity.getRole().name());
    userEntity.setUsername(entity.getUsername());
    userEntity.setRegisteredAt(LocalDateTime.now());
    userEntity.setTelegramId(botEntity.getTelegramId());
    userRepository.save(userEntity);
  }

  @Override
  public RegistrationEntity find(String token) {
    UserEntity userEntity = userRepository.findFirstByToken(token);
    return new DefaultRegistrationEntity(
        userEntity.getUsername(),
        userEntity.getFullName(),
        userEntity.getToken(),
        RoleDTO.valueOf(userEntity.getRole())
    );
  }

  @Override
  public void savePassword(ConfirmationEntity entity) {
    UserEntity userEntity = userRepository.findFirstByToken(entity.getToken());
    userEntity.setPassword(entity.getPassword());
    userEntity.setConfirmedAt(LocalDateTime.now());
    userEntity.setToken(null);
    userRepository.save(userEntity);
  }
}
