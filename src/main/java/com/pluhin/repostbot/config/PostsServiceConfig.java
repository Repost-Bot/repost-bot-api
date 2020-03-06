package com.pluhin.repostbot.config;

import com.pluhin.repostbot.model.domainid.SourceDomainType;
import com.pluhin.repostbot.service.CreatePostService;
import com.pluhin.repostbot.service.DefaultCreatePostService;
import com.pluhin.repostbot.service.conditions.AndPostCondition;
import com.pluhin.repostbot.service.conditions.HasLengthForMultipleAttachmentsPostCondition;
import com.pluhin.repostbot.service.conditions.HasLengthForOneAttachmentPostCondition;
import com.pluhin.repostbot.service.conditions.NoHistoryPostCondition;
import com.pluhin.repostbot.service.conditions.OrPostCondition;
import com.pluhin.repostbot.service.conditions.PostCondition;
import com.pluhin.repostbot.service.getposts.DefaultGetPostsService;
import com.pluhin.repostbot.service.getposts.FilterGetPostsService;
import com.pluhin.repostbot.service.getposts.GetPostsService;
import com.pluhin.repostbot.service.PostsHistoryService;
import com.pluhin.repostbot.service.getposts.VkGetPostsService;
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
    return new FilterGetPostsService(
        new VkGetPostsService(vkConfig.vkApiClient(), vkConfig.serviceActor()),
        and(
            or(
                new HasLengthForMultipleAttachmentsPostCondition(),
                new HasLengthForOneAttachmentPostCondition()
            ),
            new NoHistoryPostCondition(postsHistoryService)
        )
    );
  }

  private PostCondition or(PostCondition... conditions) {
    return new OrPostCondition(conditions);
  }

  private PostCondition and(PostCondition... conditions) {
    return new AndPostCondition(conditions);
  }
}
