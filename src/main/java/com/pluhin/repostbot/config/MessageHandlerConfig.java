package com.pluhin.repostbot.config;

import com.pluhin.repostbot.handler.ConditionalMessageHandler;
import com.pluhin.repostbot.handler.ContactMessageHandler;
import com.pluhin.repostbot.handler.DefaultMessageHandler;
import com.pluhin.repostbot.handler.EchoMessageHandler;
import com.pluhin.repostbot.handler.LoggerMessageHandler;
import com.pluhin.repostbot.handler.MessageHandler;
import com.pluhin.repostbot.handler.ReplyMessageHandler;
import com.pluhin.repostbot.handler.condition.AndMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.HasIdInReplyMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.IsAdminMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.IsNotAdminMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.MessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.ReplyMessageHandlerCondition;
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
    return DefaultMessageHandler
        .builder()
        .add(new LoggerMessageHandler())
        .add(contact())
        .add(contactAnswer())
        .build();
  }

  private MessageHandler contact() {
    return new ConditionalMessageHandler(
        isNotAdmin(),
        new ContactMessageHandler(adminsRepository)
    );
  }

  private MessageHandler contactAnswer() {
    return new ConditionalMessageHandler(
        and(
            isAdmin(),
            hasReplyMessage(),
            hasIdInReplyMessage()
        ),
        new ReplyMessageHandler()
    );
  }

  private MessageHandlerCondition isAdmin() {
    return new IsAdminMessageHandlerCondition(adminsRepository);
  }

  private MessageHandlerCondition isNotAdmin() {
    return new IsNotAdminMessageHandlerCondition(adminsRepository);
  }

  private MessageHandlerCondition hasReplyMessage() {
    return new ReplyMessageHandlerCondition();
  }

  private MessageHandlerCondition hasIdInReplyMessage() {
    return new HasIdInReplyMessageHandlerCondition();
  }

  private MessageHandlerCondition and(
      MessageHandlerCondition... conditions
  ) {
    return new AndMessageHandlerCondition(conditions);
  }
}
