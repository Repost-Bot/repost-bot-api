package com.pluhin.repostbot.handler;

import static com.pluhin.repostbot.model.user.RoleDTO.ADMIN;

import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ContactMessageHandler implements MessageHandler {

  private final UserRepository userRepository;

  public ContactMessageHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<SendMessage> handle(Update update) {
    Long originalChatId = update.getMessage().getChatId();

    String text = originalChatId + "\n" + update.getMessage().getText();
    return userRepository.findAllByRole(ADMIN.name())
        .stream()
        .map(UserEntity::getTelegramId)
        .map(id -> new SendMessage(id, text))
        .collect(Collectors.toList());
  }
}
