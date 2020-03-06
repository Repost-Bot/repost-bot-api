package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.PostsHistoryEntiity;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import java.util.List;

public interface PostsHistoryService {

  void savePost(SourceDomainId domainId, PostDTO postDTO, PostStatus status);

  void changeStatus(SourceDomainId domainId, Long sourceId, PostStatus status);

  List<Long> getSourceIdsFromHistory(SourceDomainId domainId);

  PostsHistoryEntiity getPost(SourceDomainId domainId, Long sourceId);
}
