package com.pluhin.repostbot.service;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.conditions.PostCondition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    List<PostDTO> result = new ArrayList<>();

    while (result.size() < count) {
      List<PostDTO> posts = delegate.getPosts(domainId, count - result.size(), offset);
      List<PostDTO> filtered = posts
          .stream()
          .filter(this::filter)
          .collect(Collectors.toList());

      result.addAll(filtered);
    }

    return result;
  }

  private Boolean filter(PostDTO post) {
    return conditions
        .stream()
        .map(condition -> condition.test(post))
        .filter(it -> !it)
        .findAny()
        .orElse(true);
  }
}
