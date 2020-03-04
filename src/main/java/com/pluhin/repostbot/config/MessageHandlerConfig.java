package com.pluhin.repostbot.config;

import com.pluhin.repostbot.handler.EchoMessageHandler;
import com.pluhin.repostbot.handler.MessageHandler;
import com.pluhin.repostbot.handler.SecurityMessageHandler;
import com.pluhin.repostbot.repository.AdminsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageHandlerConfig {

  private final AdminsRepository adminsRepository;

  public MessageHandlerConfig(AdminsRepository adminsRepository) {
    this.adminsRepository = adminsRepository;
  }

  @Bean
  public MessageHandler getHandlers() {
    return new SecurityMessageHandler(
        new EchoMessageHandler(),
        adminsRepository
    );
  }
}
