package com.pluhin.repostbot.model.domainid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceDomainId {

  private final SourceDomainType domainType;
  private final String domainId;

  @JsonCreator
  public SourceDomainId(
      @JsonProperty("domainType") SourceDomainType domainType,
      @JsonProperty("domainId") String domainId
  ) {
    this.domainType = domainType;
    this.domainId = domainId;
  }

  public SourceDomainType getDomainType() {
    return domainType;
  }

  public String getDomainId() {
    return domainId;
  }
}
