package com.pluhin.repostbot.model.user;

public class CreateUserDTO {

  private final String username;
  private final String fullName;
  private final Long roleId;

  public CreateUserDTO(String username, String fullName, Long roleId) {
    this.username = username;
    this.fullName = fullName;
    this.roleId = roleId;
  }

  public String getUsername() {
    return username;
  }

  public String getFullName() {
    return fullName;
  }

  public Long getRoleId() {
    return roleId;
  }
}
