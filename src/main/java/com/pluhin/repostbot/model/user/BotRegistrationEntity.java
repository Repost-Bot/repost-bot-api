package com.pluhin.repostbot.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pluhin.util.registration.model.RegistrationEntity;
import com.pluhin.util.registration.model.Role;

public class BotRegistrationEntity implements RegistrationEntity {

  private final String username;
  private final String fullName;
  private final Role role;
  private final Long telegramId;
  private final String token;

  public BotRegistrationEntity(
      @JsonProperty("username") String username,
      @JsonProperty("fullName") String fullName,
      @JsonProperty("role") Role role,
      @JsonProperty("telegramId") Long telegramId,
      @JsonProperty("token") String token
  ) {
    this.username = username;
    this.fullName = fullName;
    this.role = role;
    this.telegramId = telegramId;
    this.token = token;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getFullName() {
    return fullName;
  }

  @Override
  public Role getRole() {
    return role;
  }

  public Long getTelegramId() {
    return telegramId;
  }

  @Override
  public String getRegistrationToken() {
    return token;
  }
}
