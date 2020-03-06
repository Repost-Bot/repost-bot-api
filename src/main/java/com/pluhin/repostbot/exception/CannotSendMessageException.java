package com.pluhin.repostbot.exception;

public class CannotSendMessageException extends RuntimeException {

  public CannotSendMessageException() {
  }

  public CannotSendMessageException(String message) {
    super(message);
  }

  public CannotSendMessageException(String message, Throwable cause) {
    super(message, cause);
  }

  public CannotSendMessageException(Throwable cause) {
    super(cause);
  }
}
