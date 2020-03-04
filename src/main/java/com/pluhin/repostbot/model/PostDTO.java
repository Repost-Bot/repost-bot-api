package com.pluhin.repostbot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PostDTO {

  private final Long sourceId;
  private final List<String> images;
  private final String text;

  @JsonCreator
  public PostDTO(
      @JsonProperty("sourceId") Long sourceId,
      @JsonProperty("images") List<String> images,
      @JsonProperty("text") String text
  ) {
    this.sourceId = sourceId;
    this.images = images;
    this.text = text;
  }

  public Long getSourceId() {
    return sourceId;
  }

  public List<String> getImages() {
    return images;
  }

  public String getText() {
    return text;
  }
}
