package com.pluhin.repostbot.service;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;

public interface CreatePostService {

  void createPost(SourceDomainId domainId, PostDTO post);
}
