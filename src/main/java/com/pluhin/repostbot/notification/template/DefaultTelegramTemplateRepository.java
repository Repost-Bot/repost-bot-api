package com.pluhin.repostbot.notification.template;

import com.pluhin.repostbot.entity.TelegramTemplateEntity;
import com.pluhin.repostbot.repository.TelegramTemplateRepository;
import com.pluhin.util.notification.model.DefaultTemplate;
import com.pluhin.util.notification.model.Template;
import com.pluhin.util.notification.repository.TemplateRepository;

public class DefaultTelegramTemplateRepository implements TemplateRepository {

  private final TelegramTemplateRepository repository;

  public DefaultTelegramTemplateRepository(TelegramTemplateRepository repository) {
    this.repository = repository;
  }

  @Override
  public Template findTemplate(String templateName) {
    TelegramTemplateEntity entity = repository.findFirstByName(templateName);
    return new DefaultTemplate(entity.getTemplate());
  }
}
