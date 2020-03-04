package com.pluhin.repostbot.handler;

import com.pluhin.repostbot.entity.AdminsEntity;
import com.pluhin.repostbot.repository.AdminsRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SecurityMessageHandler implements MessageHandler {

  private static final String ERROR_MESSAGE = "Error:Access_Denied";

  private final MessageHandler delegate;
  private final AdminsRepository adminsRepository;

  public SecurityMessageHandler(MessageHandler delegate,
      AdminsRepository adminsRepository) {
    this.delegate = delegate;
    this.adminsRepository = adminsRepository;
  }

  @Override
  public SendMessage handle(Update update) {
    final Message message = update.getMessage();

    if (isSenderAdmin(message.getFrom().getUserName())) {
      return delegate.handle(update);
    } else {
      return sendAccessDeniedMessage(message.getChatId());
    }
  }

  private Boolean isSenderAdmin(String username) {
    return adminsRepository.findAll()
        .stream()
        .map(AdminsEntity::getUsername)
        .filter(username::equals)
        .findFirst()
        .map(it -> true)
        .orElse(false);
  }

  private SendMessage sendAccessDeniedMessage(Long chatId) {
    return new SendMessage(chatId, ERROR_MESSAGE);
  }
}


