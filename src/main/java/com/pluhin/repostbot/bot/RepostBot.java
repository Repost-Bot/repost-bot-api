package com.pluhin.repostbot.bot;

import java.io.File;
import java.util.List;

public interface RepostBot {

  void sendPost(File image, String text);

  void sendPost(List<File> images, String text);

  void sendMessage(List<Long> ids, String text);
}
