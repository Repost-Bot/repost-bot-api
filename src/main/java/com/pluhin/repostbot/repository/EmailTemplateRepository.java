package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.EmailTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplateEntity, Long> {

  EmailTemplateEntity findFirstByName(String name);
}
