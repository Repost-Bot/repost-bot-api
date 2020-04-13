package com.pluhin.repostbot.notification.repository;

import com.pluhin.repostbot.entity.EmailTemplateEntity;
import com.pluhin.repostbot.repository.BotEmailTemplateRepository;
import com.pluhin.util.notification.model.DefaultEmailTemplate;
import com.pluhin.util.notification.model.EmailTemplate;
import com.pluhin.util.notification.repository.EmailTemplateRepository;

public class DefaultEmailTemplateRepository implements EmailTemplateRepository {

  private final BotEmailTemplateRepository botEmailTemplateRepository;

  public DefaultEmailTemplateRepository(
      BotEmailTemplateRepository botEmailTemplateRepository) {
    this.botEmailTemplateRepository = botEmailTemplateRepository;
  }

  @Override
  public EmailTemplate findTemplate(String templateName) {
    EmailTemplateEntity entity = botEmailTemplateRepository.findFirstByName(templateName);

    return new DefaultEmailTemplate(
        entity.getSubject(),
        entity.getText()
    );
  }
}
