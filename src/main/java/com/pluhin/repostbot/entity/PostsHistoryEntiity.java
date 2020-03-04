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
@Table(name = "posts_history")
public class PostsHistoryEntiity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "source_id")
  private Long sourceId;

  @Enumerated(EnumType.STRING)
  @Column(name = "source_domain_type")
  private SourceDomainType sourceDomainType;

  @Column(name = "source_domain_id")
  private String sourceDomainId;

  @Column(name = "date_posted")
  private LocalDateTime datePosted;

  @Column
  @Enumerated(EnumType.STRING)
  private PostStatus status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSourceId() {
    return sourceId;
  }

  public void setSourceId(Long sourceId) {
    this.sourceId = sourceId;
  }

  public SourceDomainType getSourceDomainType() {
    return sourceDomainType;
  }

  public void setSourceDomainType(SourceDomainType sourceDomainType) {
    this.sourceDomainType = sourceDomainType;
  }

  public String getSourceDomainId() {
    return sourceDomainId;
  }

  public void setSourceDomainId(String sourceDomainId) {
    this.sourceDomainId = sourceDomainId;
  }

  public LocalDateTime getDatePosted() {
    return datePosted;
  }

  public void setDatePosted(LocalDateTime datePosted) {
    this.datePosted = datePosted;
  }

  public PostStatus getStatus() {
    return status;
  }

  public void setStatus(PostStatus status) {
    this.status = status;
  }
}
