package com.pluhin.repostbot.service.user;

import com.pluhin.repostbot.entity.PrivilegeEntity;
import com.pluhin.repostbot.entity.RoleEntity;
import com.pluhin.repostbot.model.user.CreateRoleDTO;
import com.pluhin.repostbot.repository.RolesRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultRolesService implements RolesService {

  private final RolesRepository rolesRepository;
  private final PrivilegesService privilegesService;

  public DefaultRolesService(RolesRepository rolesRepository,
      PrivilegesService privilegesService) {
    this.rolesRepository = rolesRepository;
    this.privilegesService = privilegesService;
  }

  @Override
  public List<RoleEntity> getAllRoles() {
    return rolesRepository.findAll();
  }

  @Override
  public RoleEntity getDefaultRole() {
    return rolesRepository.findDefault();
  }

  @Override
  public void createNewRole(CreateRoleDTO createRoleDTO) {
    List<PrivilegeEntity> privileges = privilegesService.getAll()
        .stream()
        .filter(x -> createRoleDTO.getPrivileges().contains(x.getId()))
        .collect(Collectors.toList());

    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setName(createRoleDTO.getName());
    roleEntity.setPrivileges(privileges);
    roleEntity.setDefaultRole(createRoleDTO.getDefault());

    if (roleEntity.getDefaultRole() == Boolean.TRUE) {
      RoleEntity defaultRole = rolesRepository.findDefault();
      defaultRole.setDefaultRole(Boolean.FALSE);
      rolesRepository.saveAndFlush(defaultRole);
    }

    rolesRepository.saveAndFlush(roleEntity);
  }
}
