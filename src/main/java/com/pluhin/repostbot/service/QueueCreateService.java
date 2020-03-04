package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.QueueEntity;
import java.util.List;

public interface QueueCreateService {

  List<QueueEntity> create(Long count, String queueId);
}
