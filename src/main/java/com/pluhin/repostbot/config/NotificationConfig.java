package com.pluhin.repostbot.config;

import com.pluhin.repostbot.config.settings.EmailSettings;
import com.pluhin.repostbot.notification.repository.DefaultEmailTemplateRepository;
import com.pluhin.repostbot.notification.repository.DefaultTelegramTemplateRepository;
import com.pluhin.repostbot.notification.sender.TelegramNotificationSender;
import com.pluhin.repostbot.repository.BotEmailTemplateRepository;
import com.pluhin.repostbot.repository.BotTelegramTemplateRepository;
import com.pluhin.util.notification.DefaultNotificationService;
import com.pluhin.util.notification.DictionaryNotificationService;
import com.pluhin.util.notification.LoggingNotificationService;
import com.pluhin.util.notification.NotificationService;
import com.pluhin.util.notification.ThreadedNotificationService;
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

  private final BotConfig botConfig;
  private final ConfigurationProvider configurationProvider;
  private final BotEmailTemplateRepository botEmailTemplateRepository;
  private final BotTelegramTemplateRepository botTelegramTemplateRepository;

  public NotificationConfig(BotConfig botConfig, ConfigurationProvider configurationProvider,
      BotEmailTemplateRepository botEmailTemplateRepository,
      BotTelegramTemplateRepository botTelegramTemplateRepository) {
    this.botConfig = botConfig;
    this.configurationProvider = configurationProvider;
    this.botEmailTemplateRepository = botEmailTemplateRepository;
    this.botTelegramTemplateRepository = botTelegramTemplateRepository;
  }

  @Bean
  public NotificationService notificationService() {
    Map<RecipientType, NotificationService> dictionary = new HashMap<>();
    dictionary.put(DefaultRecipientType.EMAIL, emailNotificationService());
    dictionary.put(DefaultRecipientType.TELEGRAM, telegramNotificationService());

    return new ThreadedNotificationService(
        new LoggingNotificationService(
            new DictionaryNotificationService(dictionary)
        )
    );
  }

  private NotificationService emailNotificationService() {
    EmailSettings settings = configurationProvider.bind("email", EmailSettings.class);

    TemplateRepository repository = new DefaultEmailTemplateRepository(botEmailTemplateRepository);
    TemplateProcessor processor = new DefaultEmailTemplateProcessor(new DefaultTemplateBuilder());
    NotificationSender sender = new EmailNotificationSender(
        settings.hasSsl(),
        settings.host(),
        settings.port(),
        settings.username(),
        settings.password(),
        settings.from()
    );

    return new DefaultNotificationService(
        repository,
        processor,
        sender
    );
  }

  private NotificationService telegramNotificationService() {
    TemplateRepository repository = new DefaultTelegramTemplateRepository(botTelegramTemplateRepository);
    TemplateProcessor processor = new DefaultTemplateProcessor(new DefaultTemplateBuilder());
    NotificationSender sender = new TelegramNotificationSender(botConfig.repostBot());

    return new DefaultNotificationService(
        repository,
        processor,
        sender
    );
  }
}
