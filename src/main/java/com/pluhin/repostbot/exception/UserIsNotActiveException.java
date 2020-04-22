package com.pluhin.repostbot.exception;

public class UserIsNotActiveException extends RuntimeException {

  public UserIsNotActiveException(String message) {
    super(message);
  }
}
