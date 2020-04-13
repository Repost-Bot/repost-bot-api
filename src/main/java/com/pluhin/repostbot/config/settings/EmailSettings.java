package com.pluhin.repostbot.config.settings;

public interface EmailSettings {

  String from();
  String host();
  String port();
  Boolean hasSsl();
  String username();
  String password();
}
