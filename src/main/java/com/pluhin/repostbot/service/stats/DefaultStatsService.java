package com.pluhin.repostbot.service.stats;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import com.pluhin.repostbot.entity.QueueEntity;
import com.pluhin.repostbot.model.DateStatsDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.StatsDTO;
import com.pluhin.repostbot.repository.QueueRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

  @Override
  public DateStatsDTO getDailyStats(LocalDateTime rangeFrom, LocalDateTime rangeTo) {
    Map<LocalDate, StatsDTO> grouped = queueRepository.getAllInRange(rangeFrom, rangeTo)
        .stream()
        .collect(groupingBy(entity -> entity.getDateRetrieve().toLocalDate()))
        .entrySet()
        .stream()
        .collect(toMap(
            Entry::getKey,
            it -> new StatsDTO(
                countTotalWithStatus(it.getValue(), PostStatus.DELIVERED),
                countTotalWithStatus(it.getValue(), PostStatus.DECLINED)
            )
        ));

    return new DateStatsDTO(grouped);
  }

  private Long countTotalWithStatus(PostStatus status) {
    return queueRepository.countAllByStatus(status);
  }

  private Long countTotalWithStatus(List<QueueEntity> entities, PostStatus status) {
    return entities
        .stream()
        .filter(entity -> entity.getStatus() == status)
        .count();
  }

}
