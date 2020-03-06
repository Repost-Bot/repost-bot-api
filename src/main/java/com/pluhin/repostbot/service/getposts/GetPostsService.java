package com.pluhin.repostbot.service.getposts;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import java.util.List;

public interface GetPostsService {

  List<PostDTO> getPosts(SourceDomainId domainId, Long count, Long offset);
}
