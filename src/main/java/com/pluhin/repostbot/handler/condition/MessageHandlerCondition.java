package com.pluhin.repostbot.handler.condition;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandlerCondition {

  Boolean test(Update update);
}
