package com.pluhin.repostbot.service.createqueue;

import com.pluhin.repostbot.entity.AdminsEntity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.repository.AdminsRepository;
import com.pluhin.util.notification.NotificationService;
import com.pluhin.util.notification.model.DefaultNotificationRequest;
import com.pluhin.util.notification.model.DefaultRecipient;
import com.pluhin.util.notification.model.DefaultRecipientType;
import com.pluhin.util.notification.model.NotificationRequest;
import com.pluhin.util.notification.model.Recipient;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotifyCreateQueueService implements CreateQueueService {

  private final CreateQueueService delegate;
  private final AdminsRepository adminsRepository;
  private final NotificationService notificationService;

  public NotifyCreateQueueService(CreateQueueService delegate,
      AdminsRepository adminsRepository, NotificationService notificationService) {
    this.delegate = delegate;
    this.adminsRepository = adminsRepository;
    this.notificationService = notificationService;
  }

  @Override
  public List<PostDTO> createQueue(SourceDomainId domainId, String queueId, List<Integer> hours) {
    List<PostDTO> posts = delegate.createQueue(domainId, queueId, hours);
    List<NotificationRequest> requests = createRequests(queueId);
    notificationService.send(requests);
    return posts;
  }

  private List<NotificationRequest> createRequests(String queueId) {
    return adminsRepository.findAll()
        .stream()
        .map(AdminsEntity::getUsername)
        .map(username -> creteNotificationRequest(username, queueId))
        .collect(Collectors.toList());
  }

  private NotificationRequest creteNotificationRequest(String username, String queueId) {
    Recipient recipient = new DefaultRecipient(DefaultRecipientType.TELEGRAM, username);
    Map<String, String> params = new HashMap<>();
    params.put("queueId", queueId);
    return new DefaultNotificationRequest(
        recipient,
        "QUEUE_CREATED",
        params,
        Collections.emptyList()
    );
  }
}
