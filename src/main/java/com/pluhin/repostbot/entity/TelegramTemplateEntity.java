package com.pluhin.repostbot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "telegram_template")
public class TelegramTemplateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String template;

  public Long getId() {
    return id;
  }

  public TelegramTemplateEntity setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public TelegramTemplateEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getTemplate() {
    return template;
  }

  public TelegramTemplateEntity setTemplate(String template) {
    this.template = template;
    return this;
  }
}
