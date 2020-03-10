package com.pluhin.repostbot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import java.time.LocalDateTime;

public class QueuePostDTO {

  private final Long id;
  private final String imageUrl;
  private final String text;
  private final LocalDateTime dateAdded;
  private final LocalDateTime dateRetrieve;
  private final PostStatus status;
  private final SourceDomainType domainType;
  private final String domainid;
  private final Long sourceId;
  private final String queueId;

  @JsonCreator
  public QueuePostDTO(
      @JsonProperty("id") Long id,
      @JsonProperty("imageUrl") String imageUrl,
      @JsonProperty("text") String text,
      @JsonProperty("dateAdded") LocalDateTime dateAdded,
      @JsonProperty("dateRetrieve") LocalDateTime dateRetrieve,
      @JsonProperty("status") PostStatus status,
      @JsonProperty("domainType") SourceDomainType domainType,
      @JsonProperty("domainid") String domainid,
      @JsonProperty("sourceId") Long sourceId,
      @JsonProperty("queueId") String queueId) {
    this.id = id;
    this.imageUrl = imageUrl;
    this.text = text;
    this.dateAdded = dateAdded;
    this.dateRetrieve = dateRetrieve;
    this.status = status;
    this.domainType = domainType;
    this.domainid = domainid;
    this.sourceId = sourceId;
    this.queueId = queueId;
  }

  public Long getId() {
    return id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getText() {
    return text;
  }

  public LocalDateTime getDateAdded() {
    return dateAdded;
  }

  public LocalDateTime getDateRetrieve() {
    return dateRetrieve;
  }

  public PostStatus getStatus() {
    return status;
  }

  public SourceDomainType getDomainType() {
    return domainType;
  }

  public String getDomainid() {
    return domainid;
  }

  public Long getSourceId() {
    return sourceId;
  }

  public String getQueueId() {
    return queueId;
  }
}
