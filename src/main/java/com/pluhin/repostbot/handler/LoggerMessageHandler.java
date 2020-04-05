package com.pluhin.repostbot.handler;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class LoggerMessageHandler implements MessageHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggerMessageHandler.class);

  @Override
  public List<SendMessage> handle(Update update) {
    Message message = update.getMessage();
    String text = message.getText();
    Integer userId = message.getFrom().getId();
    String senderName = message.getFrom().getFirstName() + " " + message.getFrom().getLastName();
    String log = String.join("\n", userId.toString(), senderName, text);
    LOGGER.info("log");
    return Collections.emptyList();
  }
}
