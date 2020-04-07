package com.pluhin.repostbot.config.settings;

public interface EmailSettings {

  String host();
  String from();
  Boolean hasSsl();
  String port();
  String username();
  String password();
}
