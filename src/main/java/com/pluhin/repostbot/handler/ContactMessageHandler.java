package com.pluhin.repostbot.handler;

import com.pluhin.repostbot.entity.AdminsEntity;
import com.pluhin.repostbot.repository.AdminsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ContactMessageHandler implements MessageHandler {

  private final AdminsRepository adminsRepository;

  public ContactMessageHandler(AdminsRepository adminsRepository) {
    this.adminsRepository = adminsRepository;
  }

  @Override
  public List<SendMessage> handle(Update update) {
    Long originalChatId = update.getMessage().getChatId();

    String text = originalChatId + "\n" + update.getMessage().getText();
    return adminsRepository
        .findAll()
        .stream()
        .map(AdminsEntity::getTelegramId)
        .map(id -> new SendMessage(id, text))
        .collect(Collectors.toList());
  }
}
