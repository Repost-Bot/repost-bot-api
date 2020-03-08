package com.pluhin.repostbot.config;

import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig {

  private final ConfigurationProvider configurationProvider;

  public WebMvcConfig(ConfigurationProvider configurationProvider) {
    this.configurationProvider = configurationProvider;
  }

  @Bean
  public WebMvcConfigurer configurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        String host = configurationProvider.getProperty("host.name", String.class);
        registry.addMapping("/queue/").allowedOrigins("*");
        registry.addMapping("/**").allowedOrigins(host);
      }
    };
  }
}
