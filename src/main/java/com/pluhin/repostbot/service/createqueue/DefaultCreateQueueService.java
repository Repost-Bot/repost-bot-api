package com.pluhin.repostbot.service.createqueue;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.SystemSettingsService;
import com.pluhin.repostbot.service.getposts.GetPostsService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCreateQueueService implements CreateQueueService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCreateQueueService.class);

  private final GetPostsService getPostsService;
  private final SystemSettingsService systemSettingsService;

  public DefaultCreateQueueService(GetPostsService getPostsService,
      SystemSettingsService systemSettingsService) {
    this.getPostsService = getPostsService;
    this.systemSettingsService = systemSettingsService;
  }

  @Override
  public List<PostDTO> createQueue(SourceDomainId domainId, String queueId, List<Integer> hours) {
    Long count = ((Integer) hours.size()).longValue();
    Long offset = Long.valueOf(systemSettingsService.getProperty("queue.default.offset"));
    LOGGER.info("Fetching queue poosts with count {} and offset {}", count, offset);
    return getPostsService.getPosts(domainId, count, offset);
  }
}
