package com.pluhin.repostbot;

import com.pluhin.repostbot.handler.MessageHandler;
import java.io.File;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RepostFitBot extends TelegramLongPollingBot implements RepostBot {

  private final MessageHandler messageHandler;
  private final String token;
  private final String username;
  private final String channelName;

  public RepostFitBot(MessageHandler messageHandler, String token, String username, String channelName) {
    this.messageHandler = messageHandler;
    this.token = token;
    this.username = username;
    this.channelName = channelName;
  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage sendMessage = messageHandler.handle(update);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new RuntimeException("Cannot send message", e);
    }
  }

  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void sendChannelPost(File image, String text) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setPhoto(image);
    sendPhoto.setCaption(text);
    sendPhoto.setChatId(channelName);

    try {
      execute(sendPhoto);
    } catch (TelegramApiException e) {

      throw new RuntimeException("Cannot send message", e);
    }
  }

  @Override
  public void postponePost(File image, String text) {

  }

  @Override
  public void notifyAdmins(List<Long> ids, String text) {
    ids
        .stream()
        .map(username -> createNotifyMessage(username, text))
        .forEach(msg -> {
          try {
            execute(msg);
          } catch (TelegramApiException e) {
            throw new RuntimeException("Cannot send message", e);
          }
        });
  }

  private SendMessage createNotifyMessage(Long id, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(id);
    sendMessage.setText(text);
    return sendMessage;
  }
}
