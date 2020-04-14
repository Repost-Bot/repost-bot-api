package com.pluhin.repostbot.registration.fetcher;

import com.pluhin.repostbot.model.user.BotRegistrationEntity;
import com.pluhin.util.registration.fetcher.RecipientFetcher;
import com.pluhin.util.registration.model.RegistrationEntity;

public class TelegramIdRecipientFetcher implements RecipientFetcher {

  @Override
  public String fetch(RegistrationEntity entity) {
    BotRegistrationEntity botRegistrationEntity = (BotRegistrationEntity) entity;
    return botRegistrationEntity.getTelegramId().toString();
  }
}
