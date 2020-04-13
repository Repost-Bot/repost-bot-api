package com.pluhin.repostbot.service.createqueue;

import com.pluhin.repostbot.entity.AdminsEntity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.repository.AdminsRepository;
import com.pluhin.repostbot.service.SystemSettingsService;
import com.pluhin.util.notification.NotificationService;
import com.pluhin.util.notification.model.DefaultNotificationRequest;
import com.pluhin.util.notification.model.DefaultRecipient;
import com.pluhin.util.notification.model.DefaultRecipientType;
import com.pluhin.util.notification.model.NotificationRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotifyCreateQueueService implements CreateQueueService {

  private static final String QUEUE_CREATED_TEMPLATE_NAME = "QUEUE_CREATED";

  private final CreateQueueService delegate;
  private final NotificationService notificationService;
  private final AdminsRepository adminsRepository;
  private final SystemSettingsService systemSettingsService;

  public NotifyCreateQueueService(CreateQueueService delegate,
      NotificationService notificationService, AdminsRepository adminsRepository,
      SystemSettingsService systemSettingsService) {
    this.delegate = delegate;
    this.notificationService = notificationService;
    this.adminsRepository = adminsRepository;
    this.systemSettingsService = systemSettingsService;
  }

  @Override
  public List<PostDTO> createQueue(SourceDomainId domainId, String queueId, List<Integer> hours) {
    List<PostDTO> posts = delegate.createQueue(domainId, queueId, hours);

    Map<String, String> paramsMap = createParamsMap(queueId);
    List<NotificationRequest> requests = adminsRepository.findAll()
        .stream()
        .map(AdminsEntity::getUsername)
        .map(username -> new DefaultRecipient(DefaultRecipientType.TELEGRAM, username))
        .map(recipient -> new DefaultNotificationRequest(
            recipient,
            QUEUE_CREATED_TEMPLATE_NAME,
            paramsMap
        ))
        .collect(Collectors.toList());
    notificationService.send(requests);

    return posts;
  }

  private Map<String, String> createParamsMap(String queueId) {
    Map<String, String> paramsMap = new HashMap<>();
    paramsMap.put("${QUEUE_ID}", queueId);
    paramsMap.put("${HOSTNAME}", systemSettingsService.getProperty("system.hostname"));
    return paramsMap;
  }
}
