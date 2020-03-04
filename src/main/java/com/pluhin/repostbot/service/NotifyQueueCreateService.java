package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.QueueEntity;
import java.util.List;

public class NotifyQueueCreateService implements QueueCreateService {

  private final QueueCreateService delegate;
  private final NotificationService notificationService;

  public NotifyQueueCreateService(QueueCreateService delegate,
      NotificationService notificationService) {
    this.delegate = delegate;
    this.notificationService = notificationService;
  }

  @Override
  public List<QueueEntity> create(Long count, String queueId) {
    List<QueueEntity> entities = delegate.create(count, queueId);
    notificationService.notifyQueueCreated(queueId);
    return entities;
  }
}
