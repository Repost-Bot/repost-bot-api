package com.pluhin.repostbot.config;

import com.pluhin.repostbot.model.domainid.SourceDomainType;
import com.pluhin.repostbot.service.DefaultGetSourceInfoService;
import com.pluhin.repostbot.service.GetSourceInfoService;
import com.pluhin.repostbot.service.VkGetSourceInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SourceServiceConfig {

  private final VkConfig vkConfig;

  public SourceServiceConfig(VkConfig vkConfig) {
    this.vkConfig = vkConfig;
  }

  @Bean
  public GetSourceInfoService getSourceInfoService() {
    return DefaultGetSourceInfoService
        .builder()
        .put(SourceDomainType.VK, new VkGetSourceInfoService(vkConfig.vkApiClient(), vkConfig.serviceActor()))
        .build();
  }
}
