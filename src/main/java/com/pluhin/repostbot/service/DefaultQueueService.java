package com.pluhin.repostbot.service;

import static com.pluhin.repostbot.model.PostStatus.APPROVED;
import static java.util.Arrays.asList;

import com.pluhin.repostbot.entity.QueueEntity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.QueueDTO;
import com.pluhin.repostbot.model.QueuePostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.repository.QueueRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultQueueService implements QueueService {

  private final QueueCreateService queueCreateService;
  private final SystemSettingsService systemSettingsService;
  private final QueueRepository queueRepository;
  private final CreatePostService createPostService;

  public DefaultQueueService(QueueCreateService queueCreateService,
      SystemSettingsService systemSettingsService, QueueRepository queueRepository,
      CreatePostService createPostService) {
    this.queueCreateService = queueCreateService;
    this.systemSettingsService = systemSettingsService;
    this.queueRepository = queueRepository;
    this.createPostService = createPostService;
  }

  @Override
  public void createQueue() {
    String queueId = UUID.randomUUID().toString().replace("-", "");
    String[] hours = systemSettingsService.getProperty("queue.default.hours").split(",");
    Long count = ((Integer) hours.length).longValue();
    List<QueueEntity> entities = queueCreateService.create(count, queueId);

    for(int i = 0; i < hours.length; i++) {
      QueueEntity entity = entities.get(i);
      Integer hour = Integer.parseInt(hours[i]);

      LocalDateTime dateRetrieve = LocalDateTime.now()
          .plusDays(1)
          .withHour(hour)
          .withMinute(0)
          .withSecond(0)
          .withNano(0);
      entity.setDateRetrieve(dateRetrieve);
    }

    queueRepository.saveAll(entities);
  }

  @Override
  public void processQueue() {
    queueRepository.getByStatusAndDateRetrieveLessThanEqual(APPROVED, LocalDateTime.now())
        .stream()
        .forEach(entity -> {
          SourceDomainId domainId = new SourceDomainId(
              entity.getDomainType(), entity.getDomainid()
          );
          PostDTO post = new PostDTO(
              entity.getSourceId(),
              asList(entity.getImageUrl()),
              entity.getText()
          );
          createPostService.createPost(domainId, post);
        });
  }

  @Override
  public void changeQueuePostStatus(Long id, PostStatus status) {
    QueueEntity entity = queueRepository.findById(id).get();
    entity.setStatus(status);
    queueRepository.save(entity);
  }

  @Override
  public void changeQueuePost(Long id) {
    QueueEntity entity = queueRepository.findById(id).get();
    QueueEntity newEntity = queueCreateService.create(1L, entity.getQueueId()).get(0);
    newEntity.setDateRetrieve(entity.getDateRetrieve());
    newEntity.setDateAdded(entity.getDateAdded());
    queueRepository.save(newEntity);
  }

  @Override
  public List<QueuePostDTO> getQueuePosts(String queueId) {
    return queueRepository.getAllByQueueId(queueId)
        .stream()
        .map(entity -> new QueuePostDTO(
            entity.getId(),
            entity.getImageUrl(),
            entity.getText(),
            entity.getDateAdded(),
            entity.getDateRetrieve(),
            entity.getStatus(),
            entity.getDomainType(),
            entity.getDomainid(),
            entity.getSourceId(),
            entity.getQueueId()
        ))
        .collect(Collectors.toList());
  }

  @Override
  public List<QueueDTO> getAllQueues() {
    return queueRepository.getQueueIdAndDate();
  }
}
