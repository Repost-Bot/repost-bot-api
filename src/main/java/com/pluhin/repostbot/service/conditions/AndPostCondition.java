package com.pluhin.repostbot.service.conditions;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import java.util.Arrays;
import java.util.List;

public class AndPostCondition implements PostCondition {

  private final List<PostCondition> conditions;

  public AndPostCondition(PostCondition... conditions) {
    this.conditions = Arrays.asList(conditions);
  }

  @Override
  public Boolean test(SourceDomainId domainId, PostDTO post) {
    return conditions
        .stream()
        .map(condition -> condition.test(domainId, post))
        .reduce(true, (a, b) -> a && b);
  }
}
