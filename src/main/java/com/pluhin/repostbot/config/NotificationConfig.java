package com.pluhin.repostbot.config;

import com.pluhin.repostbot.config.settings.EmailSettings;
import com.pluhin.repostbot.notification.sender.TelegramNotificationSender;
import com.pluhin.repostbot.notification.template.DefaultEmailTemplateRepository;
import com.pluhin.repostbot.notification.template.TelegramTemplateRepository;
import com.pluhin.util.notification.DefaultNotificationService;
import com.pluhin.util.notification.DictionaryNotificationService;
import com.pluhin.util.notification.NotificationService;
import com.pluhin.util.notification.builder.DefaultTemplateBuilder;
import com.pluhin.util.notification.model.DefaultRecipientType;
import com.pluhin.util.notification.model.RecipientType;
import com.pluhin.util.notification.processor.DefaultEmailTemplateProcessor;
import com.pluhin.util.notification.processor.DefaultTemplateProcessor;
import com.pluhin.util.notification.processor.TemplateProcessor;
import com.pluhin.util.notification.repository.TemplateRepository;
import com.pluhin.util.notification.sender.EmailNotificationSender;
import com.pluhin.util.notification.sender.NotificationSender;
import java.util.HashMap;
import java.util.Map;
import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

  private final ConfigurationProvider configurationProvider;

  public NotificationConfig(ConfigurationProvider configurationProvider) {
    this.configurationProvider = configurationProvider;
  }

  @Bean
  public NotificationService notificationService() {
    Map<RecipientType, NotificationService> dictionary = new HashMap<>();
    dictionary.put(DefaultRecipientType.EMAIL, emailNotificationService());
    dictionary.put(DefaultRecipientType.TELEGRAM, telegramNotificationService());

    return new DictionaryNotificationService(dictionary);
  }

  private NotificationService emailNotificationService() {
    EmailSettings emailSettings = configurationProvider.bind("email", EmailSettings.class);

    TemplateRepository templateRepository = new DefaultEmailTemplateRepository();
    TemplateProcessor processor = new DefaultEmailTemplateProcessor(new DefaultTemplateBuilder());
    NotificationSender sender = new EmailNotificationSender(
        emailSettings.hasSsl(),
        emailSettings.host(),
        emailSettings.port(),
        emailSettings.username(),
        emailSettings.password(),
        emailSettings.from()
    );
    return new DefaultNotificationService(
        templateRepository,
        processor,
        sender
    );
  }

  private NotificationService telegramNotificationService() {
    TemplateRepository templateRepository = new TelegramTemplateRepository();
    TemplateProcessor processor = new DefaultTemplateProcessor(new DefaultTemplateBuilder());
    NotificationSender sender = new TelegramNotificationSender(repostBot);
    return new DefaultNotificationService(
        templateRepository,
        processor,
        sender
    );
  }
}
