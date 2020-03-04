package com.pluhin.repostbot.service.conditions;

import com.pluhin.repostbot.model.PostDTO;

public class HasCorrectLengthCondition implements PostCondition {

  private static final Integer MAX_LENGTH = 1024;

  @Override
  public Boolean test(PostDTO post) {
    return post.getText() != null && post.getText().length() <= MAX_LENGTH;
  }
}
