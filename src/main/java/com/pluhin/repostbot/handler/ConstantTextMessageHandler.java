package com.pluhin.repostbot.handler;

import com.pluhin.repostbot.service.SystemSettingsService;
import java.util.Collections;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ConstantTextMessageHandler implements MessageHandler {

  private final SystemSettingsService systemSettingsService;
  private final String textKey;

  public ConstantTextMessageHandler(SystemSettingsService systemSettingsService, String textKey) {
    this.systemSettingsService = systemSettingsService;
    this.textKey = textKey;
  }

  @Override
  public List<SendMessage> handle(Update update) {
    Long chatId = update.getMessage().getChatId();
    String textToSend = systemSettingsService.getProperty(textKey);
    SendMessage sendMessage = new SendMessage(chatId, textToSend);
    return Collections.singletonList(sendMessage);
  }
}
