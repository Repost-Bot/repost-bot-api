package com.pluhin.repostbot.controller;

import static com.pluhin.repostbot.model.user.PrivilegeConstants.GET_SYSTEM_SETTINGS;
import static com.pluhin.repostbot.model.user.PrivilegeConstants.SAVE_SYSTEM_SETTINGS;

import com.pluhin.repostbot.model.SystemSettingsDTO;
import com.pluhin.repostbot.model.user.PrivilegeConstants;
import com.pluhin.repostbot.service.SystemSettingsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
public class SystemSettingsController {

  private final SystemSettingsService systemSettingsService;

  public SystemSettingsController(SystemSettingsService systemSettingsService) {
    this.systemSettingsService = systemSettingsService;
  }

  @Secured(GET_SYSTEM_SETTINGS)
  @GetMapping
  public List<SystemSettingsDTO> getAllSystemSettings() {
    return systemSettingsService.getAllSystemSettings();
  }

  @Secured(SAVE_SYSTEM_SETTINGS)
  @PostMapping
  public ResponseEntity<Void> saveAllSystemSettings(@RequestBody List<SystemSettingsDTO> settings) {
    systemSettingsService.saveAllSystemSettings(settings);
    return ResponseEntity.noContent().build();
  }
}
