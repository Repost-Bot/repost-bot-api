package com.pluhin.repostbot.service.getposts;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultGetPostsService implements GetPostsService {

  private final Map<SourceDomainType, GetPostsService> dictionary;

  private DefaultGetPostsService(
      Map<SourceDomainType, GetPostsService> dictionary) {
    this.dictionary = dictionary;
  }

  @Override
  public List<PostDTO> getPosts(SourceDomainId domainId, Long count, Long offset) {
    return dictionary.get(domainId.getDomainType()).getPosts(domainId, count, offset);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Map<SourceDomainType, GetPostsService> dictionary = new HashMap<>();

    public Builder put(SourceDomainType type, GetPostsService service) {
      dictionary.put(type, service);
      return this;
    }

    public GetPostsService build() {
      return new DefaultGetPostsService(dictionary);
    }
  }
}
