package com.pluhin.repostbot.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DefaultMessageHandler implements MessageHandler {

  private final List<MessageHandler> handlers;

  private DefaultMessageHandler(List<MessageHandler> handlers) {
    this.handlers = handlers;
  }

  @Override
  public List<SendMessage> handle(Update update) {
    return handlers
        .stream()
        .map(x -> x.handle(update))
        .reduce(Collections.emptyList(), accumulator());
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private final List<MessageHandler> handlers = new ArrayList<>();

    public Builder add(MessageHandler handler) {
      handlers.add(handler);
      return this;
    }

    public MessageHandler build() {
      return new DefaultMessageHandler(handlers);
    }
  }

  private BinaryOperator<List<SendMessage>> accumulator() {
    return (a, b) -> {
      if (a != null && !a.isEmpty()) {
        return a;
      } else {
        return b;
      }
    };
  }
}
