package com.pluhin.repostbot.service.getposts;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.PostsHistoryService;
import java.util.List;

public class HistoryGetPostsService implements GetPostsService {

  private final PostsHistoryService postsHistoryService;
  private final GetPostsService delegate;

  public HistoryGetPostsService(PostsHistoryService postsHistoryService,
      GetPostsService delegate) {
    this.postsHistoryService = postsHistoryService;
    this.delegate = delegate;
  }

  @Override
  public List<PostDTO> getPosts(SourceDomainId domainId, Long count, Long offset) {
    List<PostDTO> posts = delegate.getPosts(domainId, count, offset);
    posts.forEach(post -> postsHistoryService.savePost(domainId, post, PostStatus.CREATED));
    return posts;
  }
}
