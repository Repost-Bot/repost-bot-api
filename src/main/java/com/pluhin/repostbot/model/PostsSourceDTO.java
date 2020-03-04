package com.pluhin.repostbot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pluhin.repostbot.model.domainid.SourceDomainId;

public class PostsSourceDTO {

  private final SourceDomainId domainId;
  private final String imageUrl;
  private final String name;

  @JsonCreator
  public PostsSourceDTO(
      @JsonProperty("domainId") SourceDomainId domainId,
      @JsonProperty("imageUrl") String imageUrl,
      @JsonProperty("name") String name
  ) {
    this.domainId = domainId;
    this.imageUrl = imageUrl;
    this.name = name;
  }

  public SourceDomainId getDomainId() {
    return domainId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getName() {
    return name;
  }
}
