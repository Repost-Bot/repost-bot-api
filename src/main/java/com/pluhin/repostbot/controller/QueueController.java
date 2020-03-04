package com.pluhin.repostbot.controller;

import static com.pluhin.repostbot.model.PostStatus.APPROVED;
import static com.pluhin.repostbot.model.PostStatus.DECLINED;

import com.pluhin.repostbot.model.QueueDTO;
import com.pluhin.repostbot.model.QueuePostDTO;
import com.pluhin.repostbot.service.QueueService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class QueueController {

  private final Logger LOGGER = LoggerFactory.getLogger(QueueController.class);

  private final QueueService queueService;

  public QueueController(QueueService queueService) {
    this.queueService = queueService;
  }

  @GetMapping
  public List<QueueDTO> getQueues() {
    return queueService.getAllQueues();
  }

  @GetMapping("/{queueId}")
  public List<QueuePostDTO> getQueuePosts(@PathVariable String queueId) {
    return queueService.getQueuePosts(queueId);
  }

  @PostMapping("/decline/{postId}")
  public ResponseEntity<Void> declinePost(@PathVariable Long postId) {
    queueService.changeQueuePostStatus(postId, DECLINED);
    queueService.changeQueuePost(postId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/approve/{postId}")
  public ResponseEntity<Void> approvePost(@PathVariable Long postId) {
    queueService.changeQueuePostStatus(postId, APPROVED);
    return ResponseEntity.noContent().build();
  }

  @Scheduled(cron = "0/1 0 0/1 ? * * *")
  @PostMapping
  public ResponseEntity<Void> createQueue() {
    LOGGER.info("[Queue] Start creating queue");
    queueService.createQueue();
    return ResponseEntity.noContent().build();
  }

  @Scheduled(cron = "0/1 0 * ? * * *")
  @PostMapping("/process")
  public ResponseEntity<Void> processQueue() {
    LOGGER.info("[Queue] Start processing queue");
    queueService.processQueue();
    return ResponseEntity.noContent().build();
  }
}
