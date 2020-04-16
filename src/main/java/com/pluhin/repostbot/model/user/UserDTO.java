package com.pluhin.repostbot.model.user;

import java.time.LocalDateTime;

public class UserDTO {

  private final Long id;
  private final String username;
  private final String fullName;
  private final LocalDateTime registeredAt;
  private final LocalDateTime confirmedAt;

  public UserDTO(Long id, String username, String fullName, LocalDateTime registeredAt,
      LocalDateTime confirmedAt) {
    this.id = id;
    this.username = username;
    this.fullName = fullName;
    this.registeredAt = registeredAt;
    this.confirmedAt = confirmedAt;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getFullName() {
    return fullName;
  }

  public LocalDateTime getRegisteredAt() {
    return registeredAt;
  }

  public LocalDateTime getConfirmedAt() {
    return confirmedAt;
  }
}
