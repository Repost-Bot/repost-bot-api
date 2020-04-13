package com.pluhin.repostbot.notification.repository;

import com.pluhin.repostbot.entity.TelegramTemplateEntity;
import com.pluhin.repostbot.repository.BotTelegramTemplateRepository;
import com.pluhin.util.notification.model.DefaultTemplate;
import com.pluhin.util.notification.model.Template;
import com.pluhin.util.notification.repository.TemplateRepository;

public class DefaultTelegramTemplateRepository implements TemplateRepository {

  private final BotTelegramTemplateRepository botTelegramTemplateRepository;

  public DefaultTelegramTemplateRepository(
      BotTelegramTemplateRepository botTelegramTemplateRepository) {
    this.botTelegramTemplateRepository = botTelegramTemplateRepository;
  }

  @Override
  public Template findTemplate(String templateName) {
    TelegramTemplateEntity entity = botTelegramTemplateRepository.findFirstByName(templateName);
    return new DefaultTemplate(entity.getText());
  }
}
