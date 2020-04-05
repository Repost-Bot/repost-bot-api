package com.pluhin.repostbot.handler.condition;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ReplyMessageHandlerCondition implements MessageHandlerCondition {

  @Override
  public Boolean test(Update update) {
    Message replyMessage = update.getMessage().getReplyToMessage();
    return replyMessage != null;
  }
}
