package com.pluhin.repostbot.model;

import java.time.LocalDateTime;

public class QueueDTO {

  private final String queueId;
  private final LocalDateTime dateCreated;

  public QueueDTO(String queueId, LocalDateTime dateCreated) {
    this.queueId = queueId;
    this.dateCreated = dateCreated;
  }

  public String getQueueId() {
    return queueId;
  }

  public LocalDateTime getDateCreated() {
    return dateCreated;
  }
}
