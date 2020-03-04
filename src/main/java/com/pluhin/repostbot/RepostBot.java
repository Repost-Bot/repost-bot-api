package com.pluhin.repostbot;

import java.io.File;
import java.util.List;

public interface RepostBot {

  void sendChannelPost(File image, String text);

  void postponePost(File image, String text);

  void notifyAdmins(List<Long> ids, String text);
}
