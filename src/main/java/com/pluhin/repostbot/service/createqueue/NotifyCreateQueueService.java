package com.pluhin.repostbot.service.createqueue;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.NotificationService;
import java.util.List;

public class NotifyCreateQueueService implements CreateQueueService {

  private final CreateQueueService delegate;
  private final NotificationService notificationService;

  public NotifyCreateQueueService(CreateQueueService delegate,
      NotificationService notificationService) {
    this.delegate = delegate;
    this.notificationService = notificationService;
  }

  @Override
  public List<PostDTO> createQueue(SourceDomainId domainId, String queueId, List<Integer> hours) {
    List<PostDTO> posts = delegate.createQueue(domainId, queueId, hours);
    notificationService.notifyQueueCreated(queueId);
    return posts;
  }
}
