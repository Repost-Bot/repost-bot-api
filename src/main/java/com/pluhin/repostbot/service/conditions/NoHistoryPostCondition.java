package com.pluhin.repostbot.service.conditions;

import com.pluhin.repostbot.entity.PostsHistoryEntiity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.PostsHistoryService;

public class NoHistoryPostCondition implements PostCondition {

  private final PostsHistoryService postsHistoryService;

  public NoHistoryPostCondition(PostsHistoryService postsHistoryService) {
    this.postsHistoryService = postsHistoryService;
  }

  @Override
  public Boolean test(SourceDomainId domainId, PostDTO post) {
    PostsHistoryEntiity entiity = postsHistoryService.getPost(domainId, post.getSourceId());
    return entiity == null;
  }
}
