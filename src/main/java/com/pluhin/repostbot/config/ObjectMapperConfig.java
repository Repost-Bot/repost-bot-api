package com.pluhin.repostbot.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pluhin.repostbot.model.user.BotRegistrationRequest;
import com.pluhin.repostbot.model.user.RoleDTO;
import com.pluhin.util.registration.model.ConfirmationEntity;
import com.pluhin.util.registration.model.DefaultConfirmationEntity;
import com.pluhin.util.registration.model.RegistrationRequest;
import com.pluhin.util.registration.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

  @Bean
  public ObjectMapper objectMapper() {
    SimpleModule typeMappingModule = new SimpleModule("Mapping module", Version.unknownVersion());
    typeMappingModule.addAbstractTypeMapping(RegistrationRequest.class, BotRegistrationRequest.class);
    typeMappingModule.addAbstractTypeMapping(Role.class, RoleDTO.class);
    typeMappingModule.addAbstractTypeMapping(ConfirmationEntity.class, DefaultConfirmationEntity.class);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(typeMappingModule);
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }
}
