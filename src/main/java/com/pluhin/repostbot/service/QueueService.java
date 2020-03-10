package com.pluhin.repostbot.service;

import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.QueueDTO;
import com.pluhin.repostbot.model.QueuePostDTO;
import java.util.List;

public interface QueueService {

  void createQueue();

  void processQueue();

  void changeQueuePostStatus(Long id, PostStatus status);

  void changeQueuePost(Long id);

  void editPost(Long id, QueuePostDTO post);

  List<QueuePostDTO> getQueuePosts(String queueId);

  List<QueueDTO> getAllQueues();
}
