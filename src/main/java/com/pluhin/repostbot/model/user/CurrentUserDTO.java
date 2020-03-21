package com.pluhin.repostbot.model.user;

import java.util.List;

public class CurrentUserDTO {

  private final String username;
  private final List<String> privileges;

  public CurrentUserDTO(String username, List<String> privileges) {
    this.username = username;
    this.privileges = privileges;
  }

  public String getUsername() {
    return username;
  }

  public List<String> getPrivileges() {
    return privileges;
  }
}
