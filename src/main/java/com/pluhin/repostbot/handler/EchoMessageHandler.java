package com.pluhin.repostbot.handler;

import static java.util.Arrays.asList;

import java.util.Collections;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EchoMessageHandler implements MessageHandler {

  @Override
  public List<SendMessage> handle(Update update) {
    Message message = update.getMessage();
    SendMessage sendMessage = new SendMessage(message.getChatId(), message.getText());
    return Collections.singletonList(sendMessage);
  }
}
