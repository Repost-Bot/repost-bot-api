package com.pluhin.repostbot.handler.condition;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HasIdInReplyMessageHandlerCondition implements MessageHandlerCondition {

  @Override
  public Boolean test(Update update) {
    Message replyMessage = update.getMessage().getReplyToMessage();
    if (replyMessage == null) {
      return false;
    }
    String text = replyMessage.getText();
    try {
      Long id = Long.valueOf(text.split("\n")[0]);
      return id != null;
    } catch (NumberFormatException ex) {
      return false;
    }
  }
}
