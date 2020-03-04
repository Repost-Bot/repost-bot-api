package com.pluhin.repostbot.config;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkConfig {

  private final ConfigurationProvider configurationProvider;

  public VkConfig(ConfigurationProvider configurationProvider) {
    this.configurationProvider = configurationProvider;
  }

  @Bean
  public VkApiClient vkApiClient() {
    TransportClient transportClient = HttpTransportClient.getInstance();
    VkApiClient vkApiClient = new VkApiClient(transportClient, new Gson(), 1);
    return vkApiClient;
  }

  @Bean
  public ServiceActor serviceActor() {
    Integer appId = configurationProvider.getProperty("vk.app.id", Integer.class);

    return new ServiceActor(
        appId,
        configurationProvider.getProperty("vk.app.secret", String.class),
        configurationProvider.getProperty("vk.app.service", String.class)
    );
  }
}
