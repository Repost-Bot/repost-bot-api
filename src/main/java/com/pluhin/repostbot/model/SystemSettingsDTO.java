package com.pluhin.repostbot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemSettingsDTO {

  private final Long id;
  private final String key;
  private final String value;

  @JsonCreator
  public SystemSettingsDTO(
      @JsonProperty("id") Long id,
      @JsonProperty("key") String key,
      @JsonProperty("value") String value
  ) {
    this.id = id;
    this.key = key;
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}
