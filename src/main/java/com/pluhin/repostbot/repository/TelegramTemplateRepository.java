package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.TelegramTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramTemplateRepository extends JpaRepository<TelegramTemplateEntity, Long> {

  TelegramTemplateEntity findFirstByName(String name);
}
