package com.pluhin.repostbot.config;

import com.pluhin.repostbot.bot.RepostBot;
import com.pluhin.repostbot.bot.TelegramRepostBot;
import org.cfg4j.provider.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Configuration
public class BotConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(BotConfig.class);

  private final ConfigurationProvider configurationProvider;
  private final MessageHandlerConfig messageHandlerConfig;

  public BotConfig(ConfigurationProvider configurationProvider,
      MessageHandlerConfig messageHandlerConfig) {
    this.configurationProvider = configurationProvider;
    this.messageHandlerConfig = messageHandlerConfig;
  }

  @Bean
  public RepostBot repostBot() {
    ApiContextInitializer.init();

    TelegramBotsApi botsApi = new TelegramBotsApi();
    TelegramRepostBot bot = new TelegramRepostBot(
        messageHandlerConfig.getHandlers(),
        configurationProvider.getProperty("tg.bot.token", String.class),
        configurationProvider.getProperty("tg.bot.username", String.class),
        configurationProvider.getProperty("tg.channel.name", String.class)
    );

    try {
      botsApi.registerBot(bot);
    } catch (TelegramApiRequestException e) {
      LOGGER.error("Cannot start application due to telegram error", e);
      System.exit(1);
    }

    return bot;
  }
}
