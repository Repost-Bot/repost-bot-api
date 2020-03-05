package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.QueueEntity;
import com.pluhin.repostbot.repository.QueueRepository;
import java.util.List;

public class PersistingQueueCreateService implements QueueCreateService {

  private final QueueRepository queueRepository;
  private final QueueCreateService delegate;

  public PersistingQueueCreateService(QueueRepository queueRepository,
      QueueCreateService delegate) {
    this.queueRepository = queueRepository;
    this.delegate = delegate;
  }

  @Override
  public List<QueueEntity> create(String queueId, List<Integer> hours) {
    List<QueueEntity> entities = delegate.create(queueId, hours);
    queueRepository.saveAll(entities);
    return entities;
  }
}
