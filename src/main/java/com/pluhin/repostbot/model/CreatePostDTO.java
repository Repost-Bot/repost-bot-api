package com.pluhin.repostbot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pluhin.repostbot.model.domainid.SourceDomainId;

public class CreatePostDTO {

  private final SourceDomainId domainId;
  private final PostDTO post;

  @JsonCreator
  public CreatePostDTO(
      @JsonProperty("domainId") SourceDomainId domainId,
      @JsonProperty("post") PostDTO post
  ) {
    this.domainId = domainId;
    this.post = post;
  }

  public SourceDomainId getDomainId() {
    return domainId;
  }

  public PostDTO getPost() {
    return post;
  }
}
