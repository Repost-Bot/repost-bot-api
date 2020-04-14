package com.pluhin.repostbot.config;

import com.pluhin.repostbot.repository.QueueRepository;
import com.pluhin.repostbot.repository.UserRepository;
import com.pluhin.repostbot.service.DefaultQueueService;
import com.pluhin.repostbot.service.PostsHistoryService;
import com.pluhin.repostbot.service.QueueService;
import com.pluhin.repostbot.service.SystemSettingsService;
import com.pluhin.repostbot.service.createqueue.CreateQueueService;
import com.pluhin.repostbot.service.createqueue.DefaultCreateQueueService;
import com.pluhin.repostbot.service.createqueue.HistoryCreateQueueService;
import com.pluhin.repostbot.service.createqueue.NotifyCreateQueueService;
import com.pluhin.repostbot.service.createqueue.PersistingCreateQueueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueServiceConfig {

  private final PostsServiceConfig postsServiceConfig;
  private final NotificationConfig notificationConfig;
  private final SystemSettingsService systemSettingsService;
  private final QueueRepository queueRepository;
  private final PostsHistoryService postsHistoryService;
  private final UserRepository userRepository;

  public QueueServiceConfig(PostsServiceConfig postsServiceConfig,
      NotificationConfig notificationConfig, SystemSettingsService systemSettingsService,
      QueueRepository queueRepository, PostsHistoryService postsHistoryService, UserRepository userRepository) {
    this.postsServiceConfig = postsServiceConfig;
    this.notificationConfig = notificationConfig;
    this.systemSettingsService = systemSettingsService;
    this.queueRepository = queueRepository;
    this.postsHistoryService = postsHistoryService;
    this.userRepository = userRepository;
  }

  @Bean
  public QueueService queueService() {
    return new DefaultQueueService(
        createQueueService(),
        systemSettingsService,
        postsServiceConfig.createPostService(),
        queueRepository,
        postsHistoryService
    );
  }

  private CreateQueueService createQueueService() {
    return new NotifyCreateQueueService(
        new PersistingCreateQueueService(
            new HistoryCreateQueueService(
                new DefaultCreateQueueService(
                    postsServiceConfig.getPostsService(),
                    systemSettingsService
                ),
                postsHistoryService
            ),
            queueRepository
        ),
        notificationConfig.notificationService(),
        userRepository,
        systemSettingsService
    );
  }
}
