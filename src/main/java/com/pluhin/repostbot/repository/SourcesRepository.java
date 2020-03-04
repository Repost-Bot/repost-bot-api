package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.SourcesEntity;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourcesRepository extends JpaRepository<SourcesEntity, Long> {

  void deleteByDomainTypeAndDomainId(SourceDomainType domainType, String domainid);
}
