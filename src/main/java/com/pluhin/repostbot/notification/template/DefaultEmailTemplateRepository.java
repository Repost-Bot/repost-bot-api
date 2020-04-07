package com.pluhin.repostbot.notification.template;

import com.pluhin.util.notification.model.EmailTemplate;
import com.pluhin.util.notification.repository.EmailTemplateRepository;

public class DefaultEmailTemplateRepository implements EmailTemplateRepository {

  @Override
  public EmailTemplate findTemplate(String templateName) {
    return null;
  }
}
