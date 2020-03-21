package com.pluhin.repostbot.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CreateRoleDTO {

  private final String name;
  private final List<Long> privileges;
  private final Boolean isDefault;

  @JsonCreator
  public CreateRoleDTO(
      @JsonProperty("name") String name,
      @JsonProperty("privileges") List<Long> privileges,
      @JsonProperty("isDefault") Boolean isDefault) {
    this.name = name;
    this.privileges = privileges;
    this.isDefault = isDefault;
  }

  public String getName() {
    return name;
  }

  public List<Long> getPrivileges() {
    return privileges;
  }

  public Boolean getDefault() {
    return isDefault;
  }
}
