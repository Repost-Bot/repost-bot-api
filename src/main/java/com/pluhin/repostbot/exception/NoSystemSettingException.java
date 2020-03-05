package com.pluhin.repostbot.exception;

public class NoSystemSettingException extends RuntimeException {

  public NoSystemSettingException(String key) {
    super("No system setting with key " + key);
  }
}
