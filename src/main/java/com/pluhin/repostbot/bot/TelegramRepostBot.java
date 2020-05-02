package com.pluhin.repostbot.bot;

import com.pluhin.repostbot.exception.CannotSendMessageException;
import com.pluhin.repostbot.handler.MessageHandler;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
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
    List<SendMessage> sendMessage = messageHandler.handle(update);
    sendMessage
        .forEach(x -> {
          try {
            execute(x);
          } catch (TelegramApiException e) {
            throw new CannotSendMessageException("Cannot execute tg bot api method", e);
          }
        });
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
  public void sendPost(List<File> images, String text) {
    sendPost(text, images, channelName);
  }

  @Override
  public void sendPost(String text, List<File> attachments, String username) {
    if (attachments == null) {
      sendMessage(text, username);
      return;
    }
    switch (attachments.size()) {
      case 0:
        sendMessage(text, username);
        break;
      case 1:
        sendPhoto(text, attachments.get(0), username);
        break;
      default:
        sendMediaGroup(text, attachments, username);
        break;
    }
  }

  private void sendMessage(String text, String username) {
    SendMessage sendMessage = new SendMessage(username, text);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new CannotSendMessageException("Cannot execute tg bot api method", e);
    }
  }

  private void sendPhoto(String text, File attachment, String username) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setChatId(username);
    sendPhoto.setPhoto(attachment);

    if (text != null && !text.isEmpty()){
      sendPhoto.setCaption(text);
    }

    try {
      execute(sendPhoto);
    } catch (TelegramApiException e) {
      throw new CannotSendMessageException("Cannot execute tg bot api method", e);
    }
  }

  private void sendMediaGroup(String text, List<File> attachments, String username) {
    SendMediaGroup sendMediaGroup = new SendMediaGroup();
    sendMediaGroup.setChatId(username);
    sendMediaGroup.setMedia(convertFilesToInputMedia(attachments));
    Message mediaGroupMessage = null;
    try {
      mediaGroupMessage = execute(sendMediaGroup).get(0);
    } catch (TelegramApiException e) {
      throw new CannotSendMessageException("Cannot execute tg bot api method", e);
    }

    if (text == null || text.isEmpty()) {
      return;
    }

    SendMessage sendMessage = new SendMessage(username, text);
    sendMessage.setReplyToMessageId(mediaGroupMessage.getMessageId());
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new CannotSendMessageException("Cannot execute tg bot api method", e);
    }
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