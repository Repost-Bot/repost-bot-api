package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.PostsHistoryEntiity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.repository.PostsHistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultPostsHistoryService implements PostsHistoryService {

  private final PostsHistoryRepository repository;

  public DefaultPostsHistoryService(PostsHistoryRepository repository) {
    this.repository = repository;
  }

  @Override
  public void savePost(SourceDomainId domainId, PostDTO postDTO, PostStatus status) {
    PostsHistoryEntiity entiity = repository.findBySourceIdAndSourceDomainTypeAndSourceDomainId(
        postDTO.getSourceId(),
        domainId.getDomainType(),
        domainId.getDomainId()
    );

    if (entiity == null) {
      entiity = createPostHistoryEntity(domainId, postDTO, status);
    }

    entiity.setStatus(status);
    repository.save(entiity);
  }

  @Override
  public void changeStatus(SourceDomainId domainId, Long sourceId, PostStatus status) {
    PostsHistoryEntiity entiity = repository.findBySourceIdAndSourceDomainTypeAndSourceDomainId(
        sourceId,
        domainId.getDomainType(),
        domainId.getDomainId()
    );

    entiity.setStatus(status);
    repository.save(entiity);
  }

  @Override
  public List<Long> getSourceIdsFromHistory(SourceDomainId domainId) {
    return repository.findAllBySourceDomainTypeAndSourceDomainId(domainId.getDomainType(), domainId.getDomainId())
        .stream()
        .map(PostsHistoryEntiity::getSourceId)
        .collect(Collectors.toList());
  }

  private PostsHistoryEntiity createPostHistoryEntity(SourceDomainId domainId, PostDTO post, PostStatus status) {
    PostsHistoryEntiity entiity = new PostsHistoryEntiity();
    entiity.setDatePosted(LocalDateTime.now());
    entiity.setSourceDomainId(domainId.getDomainId());
    entiity.setSourceDomainType(domainId.getDomainType());
    entiity.setSourceId(post.getSourceId());
    entiity.setStatus(status);
    return entiity;
  }
}
