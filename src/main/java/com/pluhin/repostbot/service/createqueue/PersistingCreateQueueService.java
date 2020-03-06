package com.pluhin.repostbot.service.createqueue;

import com.pluhin.repostbot.entity.QueueEntity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.repository.QueueRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistingCreateQueueService implements CreateQueueService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersistingCreateQueueService.class);

  private final CreateQueueService delegate;
  private final QueueRepository queueRepository;

  public PersistingCreateQueueService(CreateQueueService delegate,
      QueueRepository queueRepository) {
    this.delegate = delegate;
    this.queueRepository = queueRepository;
  }

  @Override
  public List<PostDTO> createQueue(SourceDomainId domainId, String queueId, List<Integer> hours) {
    List<PostDTO> posts = delegate.createQueue(domainId, queueId, hours);

    List<QueueEntity> entities = new ArrayList<>(posts.size());

    for (int i = 0; i < hours.size(); i++) {
      PostDTO postDTO = posts.get(i);
      Integer hour = hours.get(i);

      QueueEntity entity = createQueueEntity(domainId, postDTO, queueId, hour);
      entities.add(entity);
    }

    LOGGER.info("Persisting {} entities for queueId {}", entities.size(), queueId);
    queueRepository.saveAll(entities);

    return posts;
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
}
