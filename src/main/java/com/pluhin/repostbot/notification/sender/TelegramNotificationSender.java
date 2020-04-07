package com.pluhin.repostbot.notification.sender;

import com.pluhin.repostbot.bot.RepostBot;
import com.pluhin.util.notification.model.Notification;
import com.pluhin.util.notification.model.Recipient;
import com.pluhin.util.notification.sender.NotificationSender;
import java.io.File;
import java.util.List;

public class TelegramNotificationSender implements NotificationSender {

  private final RepostBot repostBot;

  public TelegramNotificationSender(RepostBot repostBot) {
    this.repostBot = repostBot;
  }

  @Override
  public void send(Notification notification, Recipient recipient, List<File> attachments) {
    repostBot.sendPost(notification.getText(), attachments, recipient.getAddress());
  }
}
