package com.pluhin.repostbot.entity;

import com.pluhin.repostbot.model.domainid.SourceDomainType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sources")
public class SourcesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "domain_type")
  @Enumerated(EnumType.STRING)
  private SourceDomainType domainType;

  @Column(name = "domain_id")
  private String domainId;

  @Column
  private String name;

  @Column(name = "image_url")
  private String imageUrl;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SourceDomainType getDomainType() {
    return domainType;
  }

  public void setDomainType(SourceDomainType domainType) {
    this.domainType = domainType;
  }

  public String getDomainId() {
    return domainId;
  }

  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
