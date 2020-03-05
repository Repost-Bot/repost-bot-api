package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.SystemSettingsEntity;
import com.pluhin.repostbot.exception.NoSystemSettingException;
import com.pluhin.repostbot.model.SystemSettingsDTO;
import com.pluhin.repostbot.repository.SystemSettingsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultSystemSettingsService implements SystemSettingsService {

  private final SystemSettingsRepository repository;

  public DefaultSystemSettingsService(SystemSettingsRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<SystemSettingsDTO> getAllSystemSettings() {
    return repository.findAll()
        .stream()
        .map(entity -> new SystemSettingsDTO(entity.getId(), entity.getSettingsKey(), entity.getSettingsValue()))
        .collect(Collectors.toList());
  }

  @Override
  public void saveAllSystemSettings(List<SystemSettingsDTO> settings) {
    List<SystemSettingsEntity> entities = settings
        .stream()
        .map(this::createEntity)
        .collect(Collectors.toList());

    repository.saveAll(entities);
  }

  private SystemSettingsEntity createEntity(SystemSettingsDTO dto) {
    SystemSettingsEntity entity = new SystemSettingsEntity();
    entity.setId(dto.getId());
    entity.setSettingsKey(dto.getKey());
    entity.setSettingsValue(dto.getValue());
    return entity;
  }

  public String getProperty(String key) {
    SystemSettingsEntity entity = repository.getBySettingsKey(key);
    if (entity == null) {
      throw new NoSystemSettingException(key);
    }
    return entity.getSettingsValue();
  }
}
