package com.pluhin.repostbot.model;

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

  public QueuePostDTO(Long id, String imageUrl, String text, LocalDateTime dateAdded,
      LocalDateTime dateRetrieve, PostStatus status, SourceDomainType domainType, String domainid,
      Long sourceId, String queueId) {
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
