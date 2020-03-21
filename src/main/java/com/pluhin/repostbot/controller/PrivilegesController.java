package com.pluhin.repostbot.controller;

import com.pluhin.repostbot.entity.PrivilegeEntity;
import com.pluhin.repostbot.service.user.PrivilegesService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/privileges")
public class PrivilegesController {

  private final PrivilegesService privilegesService;

  public PrivilegesController(PrivilegesService privilegesService) {
    this.privilegesService = privilegesService;
  }

  @GetMapping
  public List<PrivilegeEntity> getAll() {
    return privilegesService.getAll();
  }
}
