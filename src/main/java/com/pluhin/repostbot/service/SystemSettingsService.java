package com.pluhin.repostbot.service;

import com.pluhin.repostbot.model.SystemSettingsDTO;
import java.util.List;

public interface SystemSettingsService {

  List<SystemSettingsDTO> getAllSystemSettings();

  void saveAllSystemSettings(List<SystemSettingsDTO> settings);

  String getProperty(String key);
}
