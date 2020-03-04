package com.pluhin.repostbot.config;

import com.pluhin.repostbot.repository.QueueRepository;
import com.pluhin.repostbot.service.DefaultQueueCreateService;
import com.pluhin.repostbot.service.DefaultQueueService;
import com.pluhin.repostbot.service.GetPostsService;
import com.pluhin.repostbot.service.HistoryGetPostsService;
import com.pluhin.repostbot.service.NotificationService;
import com.pluhin.repostbot.service.NotifyQueueCreateService;
import com.pluhin.repostbot.service.PostsHistoryService;
import com.pluhin.repostbot.service.QueueCreateService;
import com.pluhin.repostbot.service.QueueService;
import com.pluhin.repostbot.service.SystemSettingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueServiceConfig {

  private final PostsServiceConfig postsServiceConfig;
  private final NotificationService notificationService;
  private final SystemSettingsService systemSettingsService;
  private final QueueRepository queueRepository;
  private final PostsHistoryService postsHistoryService;

  public QueueServiceConfig(PostsServiceConfig postsServiceConfig,
      NotificationService notificationService, SystemSettingsService systemSettingsService,
      QueueRepository queueRepository, PostsHistoryService postsHistoryService) {
    this.postsServiceConfig = postsServiceConfig;
    this.notificationService = notificationService;
    this.systemSettingsService = systemSettingsService;
    this.queueRepository = queueRepository;
    this.postsHistoryService = postsHistoryService;
  }

  @Bean
  public QueueService queueService() {
    return new DefaultQueueService(
        queueCreateService(),
        systemSettingsService,
        queueRepository,
        postsServiceConfig.createPostService()
    );
  }

  private QueueCreateService queueCreateService() {
    return new NotifyQueueCreateService(
        new DefaultQueueCreateService(
            systemSettingsService,
            getPostsService()
        ),
        notificationService
    );
  }

  private GetPostsService getPostsService() {
    return new HistoryGetPostsService(
        postsHistoryService,
        postsServiceConfig.getPostsService()
    );
  }
}
