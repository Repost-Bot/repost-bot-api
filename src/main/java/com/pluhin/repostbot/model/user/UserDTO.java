package com.pluhin.repostbot.model.user;

import java.time.LocalDateTime;

public class UserDTO {

  private final Long id;
  private final String username;
  private final String fullName;
  private final Boolean deleted;
  private final LocalDateTime registeredAt;
  private final LocalDateTime confirmedAt;
  private final LocalDateTime deletedAt;

  public UserDTO(Long id, String username, String fullName, Boolean deleted, LocalDateTime registeredAt,
      LocalDateTime confirmedAt, LocalDateTime deletedAt) {
    this.id = id;
    this.username = username;
    this.fullName = fullName;
    this.deleted = deleted;
    this.registeredAt = registeredAt;
    this.confirmedAt = confirmedAt;
    this.deletedAt = deletedAt;
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

  public Boolean getDeleted() {
    return deleted;
  }

  public LocalDateTime getRegisteredAt() {
    return registeredAt;
  }

  public LocalDateTime getConfirmedAt() {
    return confirmedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }
}
