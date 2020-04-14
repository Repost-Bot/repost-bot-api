package com.pluhin.repostbot.config;

import com.pluhin.repostbot.handler.CompositeMessageHandler;
import com.pluhin.repostbot.handler.ConditionalMessageHandler;
import com.pluhin.repostbot.handler.ConstantTextMessageHandler;
import com.pluhin.repostbot.handler.ContactMessageHandler;
import com.pluhin.repostbot.handler.DefaultMessageHandler;
import com.pluhin.repostbot.handler.LoggerMessageHandler;
import com.pluhin.repostbot.handler.MessageHandler;
import com.pluhin.repostbot.handler.ReplyMessageHandler;
import com.pluhin.repostbot.handler.condition.AndMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.CachedMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.HasIdInReplyMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.HasSystemSettingMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.IsAdminMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.IsNotAdminMessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.MessageHandlerCondition;
import com.pluhin.repostbot.handler.condition.ReplyMessageHandlerCondition;
import com.pluhin.repostbot.repository.UserRepository;
import com.pluhin.repostbot.service.SystemSettingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageHandlerConfig {

  private final SystemSettingsService systemSettingsService;
  private final UserRepository userRepository;

  public MessageHandlerConfig(SystemSettingsService systemSettingsService,
      UserRepository userRepository) {
    this.systemSettingsService = systemSettingsService;
    this.userRepository = userRepository;
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
        CompositeMessageHandler
            .builder()
            .add(new ContactMessageHandler(userRepository))
            .add(sendFastReply())
            .build()
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

  private MessageHandler sendFastReply() {
    return new ConditionalMessageHandler(
        new CachedMessageHandlerCondition(
            new HasSystemSettingMessageHandlerCondition(
                systemSettingsService,
                "feedback.fast.reply"
            )
        ),
        new ConstantTextMessageHandler(
            systemSettingsService,
            "feedback.fast.reply"
        )
    );
  }

  private MessageHandlerCondition isAdmin() {
    return new IsAdminMessageHandlerCondition(userRepository);
  }

  private MessageHandlerCondition isNotAdmin() {
    return new IsNotAdminMessageHandlerCondition(userRepository);
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
