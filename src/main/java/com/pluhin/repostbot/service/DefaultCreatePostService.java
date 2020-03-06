package com.pluhin.repostbot.service;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainId;

public class DefaultCreatePostService implements CreatePostService {

  private final BotService botService;
  private final PostsHistoryService postsHistoryService;

  public DefaultCreatePostService(BotService botService,
      PostsHistoryService postsHistoryService) {
    this.botService = botService;
    this.postsHistoryService = postsHistoryService;
  }

  @Override
  public void createPost(SourceDomainId domainId, PostDTO post) {
    try {
      if (post.getImages().size() > 1) {
        botService.sendPost(post.getImages(), post.getText());
      } else {
        botService.sendPost(post.getImages().get(0), post.getText());
      }
      postsHistoryService.savePost(domainId, post, PostStatus.DELIVERED);
    } catch (Exception ex) {
      postsHistoryService.savePost(domainId, post, PostStatus.ERROR);
      throw ex;
    }
  }
}
