package com.pluhin.repostbot.service.conditions;

import com.pluhin.repostbot.model.PostDTO;

public interface PostCondition {

  Boolean test(PostDTO post);
}
