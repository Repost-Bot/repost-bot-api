package com.pluhin.repostbot.notification.repository;

import com.pluhin.repostbot.entity.BotNotificationEntity;
import com.pluhin.repostbot.repository.BotNotificationRepository;
import com.pluhin.util.notification.model.NotificationEntity;
import com.pluhin.util.notification.repository.NotificationRepository;

public class DefaultNotificationRepository implements NotificationRepository {

  private final BotNotificationRepository botNotificationRepository;

  public DefaultNotificationRepository(
      BotNotificationRepository botNotificationRepository) {
    this.botNotificationRepository = botNotificationRepository;
  }

  @Override
  public void save(NotificationEntity entity) {
    BotNotificationEntity notificationEntity = new BotNotificationEntity();
    notificationEntity.setAddress(entity.address());
    notificationEntity.setDateSent(entity.dateSent());
    notificationEntity.setTemplateName(entity.getTemplateName());
    notificationEntity.setType(entity.getType().name());
    botNotificationRepository.save(notificationEntity);
  }
}
