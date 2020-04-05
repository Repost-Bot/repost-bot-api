package com.pluhin.repostbot.handler;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {

  List<SendMessage> handle(Update update);
}
