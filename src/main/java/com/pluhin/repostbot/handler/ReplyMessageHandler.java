package com.pluhin.repostbot.handler;

import java.util.Collections;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ReplyMessageHandler implements MessageHandler {

  @Override
  public List<SendMessage> handle(Update update) {
    String originalText = update.getMessage().getReplyToMessage().getText();
    String[] splitted = originalText.split("\n");
    String chatIdStr = splitted[0];
    String textToSend = update.getMessage().getText();
    Long chatId = Long.valueOf(chatIdStr);
    SendMessage sendMessage = new SendMessage(chatId, textToSend);
    return Collections.singletonList(sendMessage);
  }
}
