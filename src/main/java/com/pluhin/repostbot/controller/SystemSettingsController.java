package com.pluhin.repostbot.controller;

import com.pluhin.repostbot.model.SystemSettingsDTO;
import com.pluhin.repostbot.service.SystemSettingsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
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

  @GetMapping
  public List<SystemSettingsDTO> getAllSystemSettings() {
    return systemSettingsService.getAllSystemSettings();
  }

  @PostMapping
  public ResponseEntity<Void> saveAllSystemSettings(@RequestBody List<SystemSettingsDTO> settings) {
    systemSettingsService.saveAllSystemSettings(settings);
    return ResponseEntity.noContent().build();
  }
}
