package com.pluhin.repostbot.registration.service;

import com.pluhin.repostbot.model.user.BotRegistrationEntity;
import com.pluhin.repostbot.model.user.BotRegistrationRequest;
import com.pluhin.util.registration.model.RegistrationEntity;
import com.pluhin.util.registration.model.RegistrationRequest;
import com.pluhin.util.registration.repository.RegistrationRepository;
import com.pluhin.util.registration.service.RegistrationService;
import java.util.UUID;

public class PersistRegistrationService implements RegistrationService {

  private final RegistrationRepository repository;

  public PersistRegistrationService(RegistrationRepository repository) {
    this.repository = repository;
  }

  @Override
  public RegistrationEntity register(RegistrationRequest request) {
    BotRegistrationRequest botRequest = (BotRegistrationRequest) request;
    String token = UUID.randomUUID().toString();

    RegistrationEntity entity = new BotRegistrationEntity(
        botRequest.getUsername(),
        botRequest.getFullName(),
        botRequest.getRole(),
        botRequest.getTelegramId(),
        token
    );

    repository.save(entity);

    return new BotRegistrationEntity(
        botRequest.getUsername(),
        botRequest.getFullName(),
        botRequest.getRole(),
        botRequest.getTelegramId(),
        token
    );
  }
}
