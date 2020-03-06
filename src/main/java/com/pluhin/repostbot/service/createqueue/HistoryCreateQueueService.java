package com.pluhin.repostbot.service.createqueue;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.PostsHistoryService;
import java.util.List;

public class HistoryCreateQueueService implements CreateQueueService {

  private final CreateQueueService delegate;
  private final PostsHistoryService postsHistoryService;

  public HistoryCreateQueueService(CreateQueueService delegate,
      PostsHistoryService postsHistoryService) {
    this.delegate = delegate;
    this.postsHistoryService = postsHistoryService;
  }

  @Override
  public List<PostDTO> createQueue(SourceDomainId domainId, String queueId, List<Integer> hours) {
    List<PostDTO> posts = delegate.createQueue(domainId, queueId, hours);
    posts
        .forEach(post -> postsHistoryService.savePost(domainId, post, PostStatus.CREATED));
    return posts;
  }
}
