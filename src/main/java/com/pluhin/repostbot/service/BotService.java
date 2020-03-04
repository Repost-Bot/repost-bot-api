package com.pluhin.repostbot.service;

public interface BotService {

  void sendChannelPost(String image, String text);

  void postponePost(String image, String text);
}
