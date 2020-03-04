package com.pluhin.repostbot.service;

import com.pluhin.repostbot.model.PostsSourceDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import java.util.List;

public interface SourceService {

  void createSource(SourceDomainId domainId);

  List<PostsSourceDTO> getAllSources();

  void removeSource(SourceDomainId domainId);
}
