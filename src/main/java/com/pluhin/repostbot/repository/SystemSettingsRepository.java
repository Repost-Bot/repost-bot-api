package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.SystemSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemSettingsRepository extends JpaRepository<SystemSettingsEntity, Long> {

  SystemSettingsEntity getBySettingsKey(String settingsKey);
}
