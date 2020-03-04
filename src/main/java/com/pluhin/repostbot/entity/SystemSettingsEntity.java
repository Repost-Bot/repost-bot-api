package com.pluhin.repostbot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_settings")
public class SystemSettingsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String settingsKey;

  private String settingsValue;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSettingsKey() {
    return settingsKey;
  }

  public void setSettingsKey(String settingsKey) {
    this.settingsKey = settingsKey;
  }

  public String getSettingsValue() {
    return settingsValue;
  }

  public void setSettingsValue(String settingsValue) {
    this.settingsValue = settingsValue;
  }
}
