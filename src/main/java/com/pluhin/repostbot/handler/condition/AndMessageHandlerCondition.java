package com.pluhin.repostbot.handler.condition;

import static java.util.Arrays.asList;

import java.util.List;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AndMessageHandlerCondition implements MessageHandlerCondition {

  private final List<MessageHandlerCondition> conditions;

  public AndMessageHandlerCondition(MessageHandlerCondition... conditions) {
    this.conditions = asList(conditions);
  }

  @Override
  public Boolean test(Update update) {
    return conditions
        .stream()
        .map(condition -> condition.test(update))
        .reduce(true, (a, b) -> a && b);
  }
}
