package com.pluhin.repostbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RepostBotApp {

  public static void main(String[] args) {
    SpringApplication.run(RepostBotApp.class, args);
  }
}
