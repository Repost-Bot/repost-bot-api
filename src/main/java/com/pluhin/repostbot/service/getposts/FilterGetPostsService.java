package com.pluhin.repostbot.service.getposts;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.conditions.PostCondition;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterGetPostsService implements GetPostsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterGetPostsService.class);

  private final GetPostsService delegate;
  private final List<PostCondition> conditions;

  public FilterGetPostsService(
      GetPostsService delegate,
      PostCondition... conditions) {
    this.delegate = delegate;
    this.conditions = Arrays.asList(conditions);
  }

  @Override
  public List<PostDTO> getPosts(SourceDomainId domainId, Long count, Long offset) {
    List<PostDTO> posts = delegate.getPosts(domainId, count, offset)
        .stream()
        .filter(post -> filter(domainId, post))
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

  private Boolean filter(SourceDomainId domainId, PostDTO post) {
    return conditions
        .stream()
        .map(condition -> condition.test(domainId, post))
        .reduce(true, (a, b) -> a && b);
  }
}
