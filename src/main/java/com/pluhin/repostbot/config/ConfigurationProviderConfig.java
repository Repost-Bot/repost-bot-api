package com.pluhin.repostbot.config;

import java.nio.file.Paths;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.compose.MergeConfigurationSource;
import org.cfg4j.source.context.environment.DefaultEnvironment;
import org.cfg4j.source.system.EnvironmentVariablesConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ConfigurationProviderConfig {

  @Bean
  @Profile("prod")
  public ConfigurationProvider prodConfigurationProvider() {
    return new ConfigurationProviderBuilder()
        .withConfigurationSource(new EnvironmentVariablesConfigurationSource())
        .withEnvironment(new DefaultEnvironment())
        .build();
  }

  @Bean
  @Profile("local")
  public ConfigurationProvider localConfigurationProvider() {
    return new ConfigurationProviderBuilder()
        .withConfigurationSource(
            new MergeConfigurationSource(
                new ClasspathConfigurationSource(
                    () -> Paths.get("bot.properties")
                ),
                new ClasspathConfigurationSource(
                    () -> Paths.get("application.properties")
                ),
                new EnvironmentVariablesConfigurationSource()
            )
        )
        .build();
  }
}
