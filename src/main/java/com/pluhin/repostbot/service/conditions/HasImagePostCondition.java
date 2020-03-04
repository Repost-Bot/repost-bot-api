package com.pluhin.repostbot.service.conditions;

import com.pluhin.repostbot.model.PostDTO;

public class HasImagePostCondition implements PostCondition {

  @Override
  public Boolean test(PostDTO post) {
    return post.getImages() != null && post.getImages().size() > 0;
  }
}
