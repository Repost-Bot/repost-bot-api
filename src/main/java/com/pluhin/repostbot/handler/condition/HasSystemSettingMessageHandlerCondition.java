package com.pluhin.repostbot.handler.condition;

import com.pluhin.repostbot.exception.NoSystemSettingException;
import com.pluhin.repostbot.service.SystemSettingsService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HasSystemSettingMessageHandlerCondition implements MessageHandlerCondition {

  private final SystemSettingsService systemSettingsService;
  private final String settingName;

  public HasSystemSettingMessageHandlerCondition(
      SystemSettingsService systemSettingsService, String settingName) {
    this.systemSettingsService = systemSettingsService;
    this.settingName = settingName;
  }

  @Override
  public Boolean test(Update update) {
    try {
      String setting = systemSettingsService.getProperty(settingName);
      return setting != null && setting.length() > 0;
    } catch (NoSystemSettingException ex) {
      return false;
    }
  }
}
