package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.QueueEntity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultQueueCreateService implements QueueCreateService {

  private final SystemSettingsService systemSettingsService;
  private final GetPostsService getPostsService;

  public DefaultQueueCreateService(SystemSettingsService systemSettingsService,
      GetPostsService getPostsService) {
    this.systemSettingsService = systemSettingsService;
    this.getPostsService = getPostsService;
  }

  @Override
  public List<QueueEntity> create(String queueId, List<Integer> hours) {
    SourceDomainId domainId = createDomainId();
    Long count = ((Integer) hours.size()).longValue();
    Long offset = Long.valueOf(systemSettingsService.getProperty("queue.default.offset"));
    List<PostDTO> posts = getPostsService.getPosts(domainId, count, offset);
    List<QueueEntity> entities = new ArrayList<>(posts.size());

    for (int i = 0; i < count; i++) {
      PostDTO postDTO = posts.get(i);
      Integer hour = hours.get(i);

      QueueEntity entity = createQueueEntity(domainId, postDTO, queueId, hour);
      entities.add(entity);
    }

    return entities;
  }

  private QueueEntity createQueueEntity(
      SourceDomainId domainId,
      PostDTO postDTO,
      String queueId,
      Integer hour
  ) {
    String imageUrl = Optional.ofNullable(postDTO.getImages())
        .filter(images -> images.size() > 0)
        .map(images -> images.get(0))
        .orElse(null);

    LocalDateTime dateRetrieve = LocalDateTime.now()
        .plusDays(1)
        .withHour(hour)
        .withMinute(0)
        .withSecond(0)
        .withNano(0);

    QueueEntity queueEntity = new QueueEntity();
    queueEntity.setDomainid(domainId.getDomainId());
    queueEntity.setDomainType(domainId.getDomainType());
    queueEntity.setDateAdded(LocalDateTime.now());
    queueEntity.setImageUrl(imageUrl);
    queueEntity.setText(postDTO.getText());
    queueEntity.setSourceId(postDTO.getSourceId());
    queueEntity.setStatus(PostStatus.CREATED);
    queueEntity.setQueueId(queueId);
    queueEntity.setDateRetrieve(dateRetrieve);
    return queueEntity;
  }

  private SourceDomainId createDomainId() {
    String domainId = systemSettingsService.getProperty("queue.default.source.id");
    String domainType = systemSettingsService.getProperty("queue.default.source.type");

    return new SourceDomainId(
        SourceDomainType.valueOf(domainType),
        domainId
    );
  }
}
