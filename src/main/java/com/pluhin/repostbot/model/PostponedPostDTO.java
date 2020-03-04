package com.pluhin.repostbot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class PostponedPostDTO {

  private final LocalDateTime sendDate;
  private final PostDTO post;

  @JsonCreator
  public PostponedPostDTO(
      @JsonProperty("sendDate") LocalDateTime sendDate,
      @JsonProperty("post") PostDTO post
  ) {
    this.sendDate = sendDate;
    this.post = post;
  }

  public LocalDateTime getSendDate() {
    return sendDate;
  }

  public PostDTO getPost() {
    return post;
  }
}
