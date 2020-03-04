package com.pluhin.repostbot.config;

import com.pluhin.repostbot.model.domainid.SourceDomainType;
import com.pluhin.repostbot.service.CreatePostService;
import com.pluhin.repostbot.service.DefaultCreatePostService;
import com.pluhin.repostbot.service.DefaultGetPostsService;
import com.pluhin.repostbot.service.GetPostsService;
import com.pluhin.repostbot.service.PostsHistoryService;
import com.pluhin.repostbot.service.VkGetPostsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostsServiceConfig {

  private final VkConfig vkConfig;
  private final BotConfig botConfig;
  private final PostsHistoryService postsHistoryService;

  public PostsServiceConfig(VkConfig vkConfig, BotConfig botConfig,
      PostsHistoryService postsHistoryService) {
    this.vkConfig = vkConfig;
    this.botConfig = botConfig;
    this.postsHistoryService = postsHistoryService;
  }

  @Bean
  public GetPostsService getPostsService() {
    return DefaultGetPostsService
        .builder()
        .put(SourceDomainType.VK, vkService())
        .build();
  }

  @Bean
  public CreatePostService createPostService() {
    return new DefaultCreatePostService(botConfig.botService(), postsHistoryService);
  }

  private GetPostsService vkService() {
    return new VkGetPostsService(vkConfig.vkApiClient(), vkConfig.serviceActor(), postsHistoryService);
  }
}
