package com.pluhin.repostbot.config;

import com.pluhin.repostbot.registration.DefaultRegistrationRepository;
import com.pluhin.repostbot.registration.fetcher.EmailRecipientFetcher;
import com.pluhin.repostbot.registration.fetcher.TelegramIdRecipientFetcher;
import com.pluhin.repostbot.registration.service.PersistRegistrationService;
import com.pluhin.repostbot.repository.UserRepository;
import com.pluhin.repostbot.service.SystemSettingsService;
import com.pluhin.util.notification.model.DefaultRecipientType;
import com.pluhin.util.registration.repository.RegistrationRepository;
import com.pluhin.util.registration.service.ConfirmationService;
import com.pluhin.util.registration.service.DefaultConfirmationService;
import com.pluhin.util.registration.service.DefaultValidationConfirmationService;
import com.pluhin.util.registration.service.EncryptConfirmationService;
import com.pluhin.util.registration.service.LoggingConfirmationService;
import com.pluhin.util.registration.service.LoggingRegistrationService;
import com.pluhin.util.registration.service.NotifyConfirmationService;
import com.pluhin.util.registration.service.NotifyRegistrationService;
import com.pluhin.util.registration.service.RegistrationService;
import com.pluhin.util.registration.service.ValidationConfirmationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistrationConfig {

  private final NotificationConfig notificationConfig;
  private final SecurityConfig securityConfig;
  private final UserRepository userRepository;
  private final SystemSettingsService systemSettingsService;

  public RegistrationConfig(NotificationConfig notificationConfig,
      SecurityConfig securityConfig, UserRepository userRepository,
      SystemSettingsService systemSettingsService) {
    this.notificationConfig = notificationConfig;
    this.securityConfig = securityConfig;
    this.userRepository = userRepository;
    this.systemSettingsService = systemSettingsService;
  }

  @Bean
  public RegistrationService registrationService() {
    String hostname = systemSettingsService.getProperty("system.hostname");

    return new LoggingRegistrationService(
        new NotifyRegistrationService(
            new PersistRegistrationService(
                registrationRepository()
            ),
            notificationConfig.notificationService(),
            DefaultRecipientType.EMAIL,
            new EmailRecipientFetcher(),
            hostname
        )
    );
  }

  @Bean
  public ConfirmationService confirmationService() {
    return new LoggingConfirmationService(
        new NotifyConfirmationService(
            new EncryptConfirmationService(
                new DefaultConfirmationService(registrationRepository()),
                securityConfig.passwordEncoder()
            ),
            notificationConfig.notificationService(),
            DefaultRecipientType.EMAIL,
            new EmailRecipientFetcher()
        )
    );
  }

  @Bean
  public ValidationConfirmationService validationConfirmationService() {
    return new DefaultValidationConfirmationService(registrationRepository());
  }

  @Bean
  public RegistrationRepository registrationRepository() {
    return new DefaultRegistrationRepository(userRepository);
  }
}
