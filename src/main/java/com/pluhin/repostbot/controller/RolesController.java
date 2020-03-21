package com.pluhin.repostbot.controller;

import com.pluhin.repostbot.entity.RoleEntity;
import com.pluhin.repostbot.model.user.CreateRoleDTO;
import com.pluhin.repostbot.service.user.RolesService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesController {

  private final RolesService rolesService;

  public RolesController(RolesService rolesService) {
    this.rolesService = rolesService;
  }

  @GetMapping
  public List<RoleEntity> getAllRoles() {
    return rolesService.getAllRoles();
  }

  @PostMapping
  public ResponseEntity<Void> createNewRole(@RequestBody CreateRoleDTO createRoleDTO) {
    rolesService.createNewRole(createRoleDTO);
    return ResponseEntity.noContent().build();
  }
}
