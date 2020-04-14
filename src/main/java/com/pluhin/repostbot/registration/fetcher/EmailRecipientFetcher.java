package com.pluhin.repostbot.registration.fetcher;

import com.pluhin.util.registration.fetcher.RecipientFetcher;
import com.pluhin.util.registration.model.RegistrationEntity;

public class EmailRecipientFetcher implements RecipientFetcher {

  @Override
  public String fetch(RegistrationEntity entity) {
    return entity.getUsername();
  }
}
