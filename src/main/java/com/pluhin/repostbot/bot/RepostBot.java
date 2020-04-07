package com.pluhin.repostbot.bot;

import java.io.File;
import java.util.List;

public interface RepostBot {

  void sendPost(List<File> images, String text);

  void sendPost(String text, List<File> attachments, String username);
}
