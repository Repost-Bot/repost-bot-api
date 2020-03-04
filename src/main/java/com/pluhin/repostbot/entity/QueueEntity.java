package com.pluhin.repostbot.entity;

import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "queue")
public class QueueEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "image_url")
  private String imageUrl;

  @Column
  private String text;

  @Column(name = "date_added")
  private LocalDateTime dateAdded;

  @Column(name = "date_retrieve")
  private LocalDateTime dateRetrieve;

  @Enumerated(EnumType.STRING)
  @Column
  private PostStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "domain_type")
  private SourceDomainType domainType;

  @Column(name = "domain_id")
  private String domainid;

  @Column(name = "source_id")
  private Long sourceId;

  @Column(name = "queue_id")
  private String queueId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(LocalDateTime dateAdded) {
    this.dateAdded = dateAdded;
  }

  public LocalDateTime getDateRetrieve() {
    return dateRetrieve;
  }

  public void setDateRetrieve(LocalDateTime dateRetrieve) {
    this.dateRetrieve = dateRetrieve;
  }

  public PostStatus getStatus() {
    return status;
  }

  public void setStatus(PostStatus status) {
    this.status = status;
  }

  public SourceDomainType getDomainType() {
    return domainType;
  }

  public void setDomainType(SourceDomainType domainType) {
    this.domainType = domainType;
  }

  public String getDomainid() {
    return domainid;
  }

  public void setDomainid(String domainid) {
    this.domainid = domainid;
  }

  public Long getSourceId() {
    return sourceId;
  }

  public void setSourceId(Long sourceId) {
    this.sourceId = sourceId;
  }

  public String getQueueId() {
    return queueId;
  }

  public void setQueueId(String queueId) {
    this.queueId = queueId;
  }
}
