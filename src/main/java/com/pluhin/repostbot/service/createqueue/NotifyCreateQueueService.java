package com.pluhin.repostbot.service.createqueue;

import static com.pluhin.repostbot.model.user.RoleDTO.ADMIN;

import com.pluhin.repostbot.entity.UserEntity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.repository.UserRepository;
import com.pluhin.repostbot.service.SystemSettingsService;
import com.pluhin.util.notification.NotificationService;
import com.pluhin.util.notification.model.DefaultNotificationRequest;
import com.pluhin.util.notification.model.DefaultRecipient;
import com.pluhin.util.notification.model.DefaultRecipientType;
import com.pluhin.util.notification.model.NotificationRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class NotifyCreateQueueService implements CreateQueueService {

  private static final String QUEUE_CREATED_TEMPLATE_NAME = "QUEUE_CREATED";

  private final CreateQueueService delegate;
  private final NotificationService notificationService;
  private final UserRepository userRepository;
  private final SystemSettingsService systemSettingsService;

  public NotifyCreateQueueService(CreateQueueService delegate,
      NotificationService notificationService, UserRepository userRepository,
      SystemSettingsService systemSettingsService) {
    this.delegate = delegate;
    this.notificationService = notificationService;
    this.userRepository = userRepository;
    this.systemSettingsService = systemSettingsService;
  }

  @Override
  public List<PostDTO> createQueue(SourceDomainId domainId, String queueId, List<Integer> hours) {
    List<PostDTO> posts = delegate.createQueue(domainId, queueId, hours);

    Map<String, String> paramsMap = createParamsMap(queueId);
    List<NotificationRequest> requests = userRepository.findAllByRole(ADMIN.name())
        .stream()
        .map(UserEntity::getTelegramId)
        .map(Objects::toString)
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
