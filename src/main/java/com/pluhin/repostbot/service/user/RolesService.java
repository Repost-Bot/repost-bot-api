package com.pluhin.repostbot.service.user;

import com.pluhin.repostbot.entity.RoleEntity;
import com.pluhin.repostbot.model.user.CreateRoleDTO;
import java.util.List;

public interface RolesService {

  List<RoleEntity> getAllRoles();

  RoleEntity getDefaultRole();

  void createNewRole(CreateRoleDTO createRoleDTO);
}
