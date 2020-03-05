package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.QueueEntity;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.QueueDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QueueRepository extends JpaRepository<QueueEntity, Long> {

  List<QueueEntity> getAllByQueueId(String queueId);

  @Query("SELECT "
      + "new com.pluhin.repostbot.model.QueueDTO(q.queueId, q.dateAdded) "
      + "FROM QueueEntity q "
      + "GROUP BY q.queueId, q.dateAdded")
  List<QueueDTO> getQueueIdAndDate();

  List<QueueEntity> getByStatusAndDateRetrieveLessThanEqual(PostStatus status, LocalDateTime date);

  Long countAllByStatus(PostStatus status);
}
