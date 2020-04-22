package com.pluhin.repostbot.config;

import com.google.common.collect.ImmutableMap;
import com.pluhin.repostbot.config.settings.EmailSettings;
import com.pluhin.repostbot.notification.repository.DefaultEmailTemplateRepository;
import com.pluhin.repostbot.notification.repository.DefaultNotificationRepository;
import com.pluhin.repostbot.notification.repository.DefaultTelegramTemplateRepository;
import com.pluhin.repostbot.notification.sender.TelegramNotificationSender;
import com.pluhin.repostbot.repository.BotEmailTemplateRepository;
import com.pluhin.repostbot.repository.BotNotificationRepository;
import com.pluhin.repostbot.repository.BotTelegramTemplateRepository;
import com.pluhin.util.notification.AsyncNotificationService;
import com.pluhin.util.notification.DefaultNotificationService;
import com.pluhin.util.notification.DictionaryNotificationService;
import com.pluhin.util.notification.LoggingNotificationService;
import com.pluhin.util.notification.NotificationService;
import com.pluhin.util.notification.PersistNotificationService;
import com.pluhin.util.notification.builder.DefaultTemplateBuilder;
import com.pluhin.util.notification.model.DefaultRecipientType;
import com.pluhin.util.notification.model.RecipientType;
import com.pluhin.util.notification.processor.DefaultEmailTemplateProcessor;
import com.pluhin.util.notification.processor.DefaultTemplateProcessor;
import com.pluhin.util.notification.processor.TemplateProcessor;
import com.pluhin.util.notification.repository.NotificationRepository;
import com.pluhin.util.notification.repository.TemplateRepository;
import com.pluhin.util.notification.sender.EmailNotificationSender;
import com.pluhin.util.notification.sender.NotificationSender;
import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

  private final BotConfig botConfig;
  private final ConfigurationProvider configurationProvider;
  private final BotEmailTemplateRepository botEmailTemplateRepository;
  private final BotTelegramTemplateRepository botTelegramTemplateRepository;
  private final BotNotificationRepository botNotificationRepository;

  public NotificationConfig(BotConfig botConfig, ConfigurationProvider configurationProvider,
      BotEmailTemplateRepository botEmailTemplateRepository,
      BotTelegramTemplateRepository botTelegramTemplateRepository,
      BotNotificationRepository botNotificationRepository) {
    this.botConfig = botConfig;
    this.configurationProvider = configurationProvider;
    this.botEmailTemplateRepository = botEmailTemplateRepository;
    this.botTelegramTemplateRepository = botTelegramTemplateRepository;
    this.botNotificationRepository = botNotificationRepository;
  }

  @Bean
  public NotificationService notificationService() {
    return new AsyncNotificationService(
        new PersistNotificationService(
            new LoggingNotificationService(
                new DictionaryNotificationService(
                    ImmutableMap.<RecipientType, NotificationService>builder()
                        .put(DefaultRecipientType.EMAIL, emailNotificationService())
                        .put(DefaultRecipientType.TELEGRAM, telegramNotificationService())
                        .build()
                )
            ),
            notificationRepository()
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

  private NotificationRepository notificationRepository() {
    return new DefaultNotificationRepository(botNotificationRepository);
  }
}
