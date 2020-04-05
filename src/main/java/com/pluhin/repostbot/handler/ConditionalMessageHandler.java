package com.pluhin.repostbot.handler;

import com.pluhin.repostbot.handler.condition.MessageHandlerCondition;
import java.util.Collections;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ConditionalMessageHandler implements MessageHandler {

  private final MessageHandlerCondition condition;
  private final MessageHandler handler;

  public ConditionalMessageHandler(MessageHandlerCondition condition,
      MessageHandler handler) {
    this.condition = condition;
    this.handler = handler;
  }

  @Override
  public List<SendMessage> handle(Update update) {
    if (condition.test(update)) {
      return handler.handle(update);
    }
    return Collections.emptyList();
  }
}
