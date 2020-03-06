package com.pluhin.repostbot.service;

import com.pluhin.repostbot.bot.RepostBot;
import com.pluhin.repostbot.entity.AdminsEntity;
import com.pluhin.repostbot.repository.AdminsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultNotificationService implements NotificationService {

  private final RepostBot repostBot;
  private final AdminsRepository adminsRepository;
  private final SystemSettingsService systemSettingsService;

  public DefaultNotificationService(RepostBot repostBot,
      AdminsRepository adminsRepository, SystemSettingsService systemSettingsService) {
    this.repostBot = repostBot;
    this.adminsRepository = adminsRepository;
    this.systemSettingsService = systemSettingsService;
  }

  @Override
  public void notifyQueueCreated(String queueId) {
    List<Long> adminsId = adminsRepository.findAll()
        .stream()
        .map(AdminsEntity::getTelegramId)
        .collect(Collectors.toList());

    repostBot.sendMessage(adminsId, getCreatedQueueMessage(queueId));
  }

  private String getCreatedQueueMessage(String queueId) {
    String hostname = systemSettingsService.getProperty("system.hostname");
    return "Created queue: " + hostname + "/queue/" + queueId;
  }
}
