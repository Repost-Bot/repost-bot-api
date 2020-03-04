package com.pluhin.repostbot.config;

import com.pluhin.repostbot.RepostBot;
import com.pluhin.repostbot.RepostFitBot;
import com.pluhin.repostbot.repository.AdminsRepository;
import com.pluhin.repostbot.service.BotService;
import com.pluhin.repostbot.service.DefaultBotService;
import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Configuration
public class BotConfig {

  private final ConfigurationProvider configurationProvider;
  private final MessageHandlerConfig messageHandlerConfig;
  private final AdminsRepository adminsRepository;

  public BotConfig(ConfigurationProvider configurationProvider,
      MessageHandlerConfig messageHandlerConfig, AdminsRepository adminsRepository) {
    this.configurationProvider = configurationProvider;
    this.messageHandlerConfig = messageHandlerConfig;
    this.adminsRepository = adminsRepository;
  }

  @Bean
  public BotService botService() {
    return new DefaultBotService(
        adminsRepository,
        repostBot()
    );
  }

  @Bean
  public RepostBot repostBot() {
    ApiContextInitializer.init();

    TelegramBotsApi botsApi = new TelegramBotsApi();
    RepostFitBot bot = new RepostFitBot(
        messageHandlerConfig.getHandlers(),
        configurationProvider.getProperty("tg.bot.token", String.class),
        configurationProvider.getProperty("tg.bot.username", String.class),
        configurationProvider.getProperty("tg.channel.name", String.class)
    );

    try {
      botsApi.registerBot(bot);
    } catch (TelegramApiRequestException e) {
      e.printStackTrace();
    }

    return bot;
  }
}
