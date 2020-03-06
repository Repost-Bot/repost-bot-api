package com.pluhin.repostbot.bot;

import com.pluhin.repostbot.exception.CannotSendMessageException;
import com.pluhin.repostbot.handler.MessageHandler;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramRepostBot extends TelegramLongPollingBot implements RepostBot {

  private final MessageHandler messageHandler;
  private final String token;
  private final String username;
  private final String channelName;

  public TelegramRepostBot(MessageHandler messageHandler, String token, String username, String channelName) {
    this.messageHandler = messageHandler;
    this.token = token;
    this.username = username;
    this.channelName = channelName;
  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage sendMessage = messageHandler.handle(update);
    executeMethod(sendMessage);
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
  public void sendPost(File image, String text) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setPhoto(image);
    sendPhoto.setCaption(text);
    sendPhoto.setChatId(channelName);

    executeMethod(sendPhoto);
  }

  @Override
  public void sendPost(List<File> images, String text) {
    SendMediaGroup sendMediaGroup = new SendMediaGroup();
    sendMediaGroup.setChatId(channelName);
    sendMediaGroup.setMedia(convertFilesToInputMedia(images));

    SendMessage sendMessage = new SendMessage(channelName, text);
    executeMethod(sendMediaGroup);
    executeMethod(sendMessage);
  }

  @Override
  public void sendMessage(List<Long> ids, String text) {
    ids
        .stream()
        .map(id -> createSendMessage(id, text))
        .forEach(this::executeMethod);
  }

  private void executeMethod(SendPhoto sendPhoto) {
    try {
      execute(sendPhoto);
    } catch (TelegramApiException e) {
      throw new CannotSendMessageException("Cannot execute tg bot api method", e);
    }
  }

  private void executeMethod(SendMessage sendMessage) {
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new CannotSendMessageException("Cannot execute tg bot api method", e);
    }
  }

  private void executeMethod(SendMediaGroup sendMediaGroup) {
    try {
      execute(sendMediaGroup);
    } catch (TelegramApiException e) {
      throw new CannotSendMessageException("Cannot execute tg bot api method", e);
    }
  }

  private SendMessage createSendMessage(Long id, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(id);
    sendMessage.setText(text);
    return sendMessage;
  }

  private List<InputMedia> convertFilesToInputMedia(List<File> files) {
    return files
        .stream()
        .map(file ->{
          InputMedia inputMedia = new InputMediaPhoto();
          inputMedia.setMedia(file, file.getName());
          return inputMedia;
        })
        .collect(Collectors.toList());
  }
}
