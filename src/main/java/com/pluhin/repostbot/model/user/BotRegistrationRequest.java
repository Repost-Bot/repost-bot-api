package com.pluhin.repostbot.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pluhin.util.registration.model.RegistrationRequest;
import com.pluhin.util.registration.model.Role;

public class BotRegistrationRequest implements RegistrationRequest {

  private final String username;
  private final String fullName;
  private final Role role;
  private final Long telegramId;

  public BotRegistrationRequest(
      @JsonProperty("username") String username,
      @JsonProperty("fullName") String fullName,
      @JsonProperty("role") Role role,
      @JsonProperty("telegramId") Long telegramId
  ) {
    this.username = username;
    this.fullName = fullName;
    this.role = role;
    this.telegramId = telegramId;
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
}
