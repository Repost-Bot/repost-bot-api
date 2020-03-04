package com.pluhin.repostbot.exception;

public class GetSourceInfoException extends RuntimeException {

  public GetSourceInfoException() {
  }

  public GetSourceInfoException(String message) {
    super(message);
  }

  public GetSourceInfoException(Throwable cause) {
    super(cause);
  }
}
