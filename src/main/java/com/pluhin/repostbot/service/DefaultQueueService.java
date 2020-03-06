package com.pluhin.repostbot.service;

import static com.pluhin.repostbot.model.PostStatus.APPROVED;
import static com.pluhin.repostbot.model.PostStatus.DELIVERED;
import static java.util.Arrays.asList;

import com.pluhin.repostbot.entity.QueueEntity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.QueueDTO;
import com.pluhin.repostbot.model.QueuePostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import com.pluhin.repostbot.repository.QueueRepository;
import com.pluhin.repostbot.service.createqueue.CreateQueueService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultQueueService implements QueueService {

  private final CreateQueueService createQueueService;
  private final SystemSettingsService systemSettingsService;
  private final CreatePostService createPostService;
  private final QueueRepository queueRepository;

  public DefaultQueueService(CreateQueueService createQueueService,
      SystemSettingsService systemSettingsService, CreatePostService createPostService,
      QueueRepository queueRepository) {
    this.createQueueService = createQueueService;
    this.systemSettingsService = systemSettingsService;
    this.createPostService = createPostService;
    this.queueRepository = queueRepository;
  }

  @Override
  public void createQueue() {
    String queueId = UUID.randomUUID().toString().replace("-", "");
    List<Integer> hours = getHours();
    createQueueService.createQueue(createDomainId(), queueId, hours);
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
          changeQueuePostStatus(entity.getId(), DELIVERED);
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
    List<Integer> hour = Arrays.asList(entity.getDateRetrieve().getHour());
    createQueueService.createQueue(createDomainId(), entity.getQueueId(), hour).get(0);
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

  private List<Integer> getHours() {
    return Arrays.stream(systemSettingsService.getProperty("queue.default.hours").split(","))
        .map(Integer::valueOf)
        .collect(Collectors.toList());
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
