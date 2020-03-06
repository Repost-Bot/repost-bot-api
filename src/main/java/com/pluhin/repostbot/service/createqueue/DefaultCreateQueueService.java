package com.pluhin.repostbot.service.createqueue;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.SystemSettingsService;
import com.pluhin.repostbot.service.getposts.GetPostsService;
import java.util.List;

public class DefaultCreateQueueService implements CreateQueueService {

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
    return getPostsService.getPosts(domainId, count, offset);
  }
}
