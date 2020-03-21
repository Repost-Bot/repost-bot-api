package com.pluhin.repostbot.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Boolean defaultRole;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  private List<PrivilegeEntity> privileges;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getDefaultRole() {
    return defaultRole;
  }

  public void setDefaultRole(Boolean defaultRole) {
    this.defaultRole = defaultRole;
  }

  public List<PrivilegeEntity> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<PrivilegeEntity> privileges) {
    this.privileges = privileges;
  }
}
