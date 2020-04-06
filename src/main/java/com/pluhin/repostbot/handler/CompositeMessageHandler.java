package com.pluhin.repostbot.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CompositeMessageHandler implements MessageHandler {

  private final List<MessageHandler> handlers;

  private CompositeMessageHandler(List<MessageHandler> handlers) {
    this.handlers = handlers;
  }

  @Override
  public List<SendMessage> handle(Update update) {
    return handlers
        .stream()
        .map(x -> x.handle(update))
        .reduce(new ArrayList<>(), accumulator());
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
      return new CompositeMessageHandler(handlers);
    }
  }

  private BinaryOperator<List<SendMessage>> accumulator() {
    return (a, b) -> {
      a.addAll(b);
      return a;
    };
  }
}
