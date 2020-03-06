package com.pluhin.repostbot.service.createqueue;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import java.util.List;

public interface CreateQueueService {

  List<PostDTO> createQueue(SourceDomainId domainId, String queueId, List<Integer> hours);
}
