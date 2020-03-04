package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.PostsHistoryEntiity;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsHistoryRepository extends JpaRepository<PostsHistoryEntiity, Long> {

  List<PostsHistoryEntiity> findAllBySourceDomainTypeAndSourceDomainId(SourceDomainType domainType, String domainId);

  PostsHistoryEntiity findBySourceIdAndSourceDomainTypeAndSourceDomainId(
      Long sourceId,
      SourceDomainType domainType,
      String domainId
  );
}
