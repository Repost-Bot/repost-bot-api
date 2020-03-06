package com.pluhin.repostbot.service;

import java.util.List;

public interface BotService {

  void sendPost(String image, String text);

  void sendPost(List<String> images, String text);
}
