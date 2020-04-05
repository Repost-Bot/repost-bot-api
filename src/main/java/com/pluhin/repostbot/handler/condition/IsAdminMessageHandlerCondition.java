package com.pluhin.repostbot.handler.condition;

import com.pluhin.repostbot.entity.AdminsEntity;
import com.pluhin.repostbot.repository.AdminsRepository;
import java.util.Objects;
import org.telegram.telegrambots.meta.api.objects.Update;

public class IsAdminMessageHandlerCondition implements MessageHandlerCondition {

  private final AdminsRepository adminsRepository;

  public IsAdminMessageHandlerCondition(AdminsRepository adminsRepository) {
    this.adminsRepository = adminsRepository;
  }

  @Override
  public Boolean test(Update update) {
    Long userId = update.getMessage().getChatId();
    return adminsRepository.findAll()
        .stream()
        .map(AdminsEntity::getTelegramId)
        .anyMatch( x -> Objects.equals(x, userId));
  }
}
