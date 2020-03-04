package com.pluhin.repostbot.service;

import com.pluhin.repostbot.RepostBot;
import com.pluhin.repostbot.entity.AdminsEntity;
import com.pluhin.repostbot.repository.AdminsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultNotificationService implements NotificationService {

  private final RepostBot repostBot;
  private final AdminsRepository adminsRepository;

  public DefaultNotificationService(RepostBot repostBot,
      AdminsRepository adminsRepository) {
    this.repostBot = repostBot;
    this.adminsRepository = adminsRepository;
  }

  @Override
  public void notifyQueueCreated(String queueId) {
    List<Long> adminsId = adminsRepository.findAll()
        .stream()
        .map(AdminsEntity::getTelegramId)
        .collect(Collectors.toList());

    repostBot.notifyAdmins(adminsId, getCreatedQueueMessage(queueId));
  }

  private String getCreatedQueueMessage(String queueId) {
    return String.format("Created queue with id %s", queueId);
  }
}
