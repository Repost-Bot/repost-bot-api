package com.pluhin.repostbot.service.stats;

import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.StatsDTO;
import com.pluhin.repostbot.repository.QueueRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultStatsService implements StatsService {

  private final QueueRepository queueRepository;

  public DefaultStatsService(QueueRepository queueRepository) {
    this.queueRepository = queueRepository;
  }

  @Override
  public StatsDTO getStats() {
    return new StatsDTO(
        countTotalWithStatus(PostStatus.DELIVERED),
        countTotalWithStatus(PostStatus.DECLINED)
    );
  }

  private Long countTotalWithStatus(PostStatus status) {
    return queueRepository.countAllByStatus(status);
  }
}
