package com.pluhin.repostbot.handler.condition;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CachedMessageHandlerCondition implements MessageHandlerCondition {

  private final MessageHandlerCondition delegate;
  private volatile Boolean cached = false;
  private Boolean value = null;

  public CachedMessageHandlerCondition(MessageHandlerCondition delegate) {
    this.delegate = delegate;
  }

  @Override
  public Boolean test(Update update) {
    if (!cached) {
      Boolean result = delegate.test(update);
      value = result;
      cached = true;
    }
    return value;
  }
}
