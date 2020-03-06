package com.pluhin.repostbot.service.getposts;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.conditions.PostCondition;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterGetPostsService implements GetPostsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterGetPostsService.class);

  private final GetPostsService delegate;
  private final PostCondition condition;

  public FilterGetPostsService(GetPostsService delegate,
      PostCondition condition) {
    this.delegate = delegate;
    this.condition = condition;
  }

  @Override
  public List<PostDTO> getPosts(SourceDomainId domainId, Long count, Long offset) {
    List<PostDTO> posts = delegate.getPosts(domainId, count, offset)
        .stream()
        .filter(post -> condition.test(domainId, post))
        .collect(Collectors.toList());

    LOGGER.info("Fetched {} posts ", posts.size());

    if (posts.size() < count) {
      return Stream.concat(
          posts.stream(),
          getPosts(domainId, count - posts.size(), offset + count).stream()
      )
          .collect(Collectors.toList());
    }

    return posts;

}
