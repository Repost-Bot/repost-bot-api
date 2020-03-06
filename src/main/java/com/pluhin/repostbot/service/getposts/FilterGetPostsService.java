package com.pluhin.repostbot.service.getposts;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.conditions.PostCondition;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterGetPostsService implements GetPostsService {

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
        .filter(this::filter)
        .collect(Collectors.toList());

    if (posts.size() < count) {
      return Stream.concat(
          posts.stream(),
          getPosts(domainId, count - posts.size(), offset + count).stream()
      )
          .collect(Collectors.toList());
    }

    return posts;
  }

  private Boolean filter(PostDTO post) {
    return conditions
        .stream()
        .map(condition -> condition.test(post))
        .reduce(true, (a, b) -> a && b);
  }
}
