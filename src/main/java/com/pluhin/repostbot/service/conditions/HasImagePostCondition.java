package com.pluhin.repostbot.service.conditions;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;

public class HasImagePostCondition implements PostCondition {

  @Override
  public Boolean test(SourceDomainId domainId, PostDTO post) {
    return post.getImages() != null && post.getImages().size() > 0;
  }
}
