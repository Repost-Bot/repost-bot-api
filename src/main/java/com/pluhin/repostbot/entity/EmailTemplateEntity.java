package com.pluhin.repostbot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_template")
public class EmailTemplateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String subject;

  private String template;

  public Long getId() {
    return id;
  }

  public EmailTemplateEntity setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public EmailTemplateEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public EmailTemplateEntity setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getTemplate() {
    return template;
  }

  public EmailTemplateEntity setTemplate(String template) {
    this.template = template;
    return this;
  }
}
