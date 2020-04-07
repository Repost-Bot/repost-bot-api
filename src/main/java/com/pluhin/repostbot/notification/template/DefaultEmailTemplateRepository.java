package com.pluhin.repostbot.notification.template;

import com.pluhin.repostbot.entity.EmailTemplateEntity;
import com.pluhin.util.notification.model.DefaultEmailTemplate;
import com.pluhin.util.notification.model.EmailTemplate;
import com.pluhin.util.notification.repository.EmailTemplateRepository;

public class DefaultEmailTemplateRepository implements EmailTemplateRepository {

  private final com.pluhin.repostbot.repository.EmailTemplateRepository emailTemplateRepository;

  public DefaultEmailTemplateRepository(com.pluhin.repostbot.repository.EmailTemplateRepository emailTemplateRepository) {
    this.emailTemplateRepository = emailTemplateRepository;
  }

  @Override
  public EmailTemplate findTemplate(String templateName) {
    EmailTemplateEntity entity = emailTemplateRepository.findFirstByName(templateName);
    return new DefaultEmailTemplate(entity.getSubject(), entity.getTemplate());
  }
}
