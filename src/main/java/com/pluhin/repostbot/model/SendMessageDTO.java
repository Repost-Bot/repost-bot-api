package com.pluhin.repostbot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageDTO {

  private final String text;

  @JsonCreator
  public SendMessageDTO(
      @JsonProperty("text") String text
  ) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
