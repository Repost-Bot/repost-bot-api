package com.pluhin.repostbot.handler.condition;

import static com.pluhin.repostbot.model.user.RoleDTO.ADMIN;

import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.repository.UserRepository;
import java.util.Objects;
import org.telegram.telegrambots.meta.api.objects.Update;

public class IsAdminMessageHandlerCondition implements MessageHandlerCondition {

  private final UserRepository userRepository;

  public IsAdminMessageHandlerCondition(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Boolean test(Update update) {
    Long userId = update.getMessage().getChatId();
    return userRepository.findAllByRole(ADMIN.name())
        .stream()
        .map(UserEntity::getTelegramId)
        .anyMatch( x -> Objects.equals(x, userId));
  }
}
