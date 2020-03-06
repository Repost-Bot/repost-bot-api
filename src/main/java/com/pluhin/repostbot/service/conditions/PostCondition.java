package com.pluhin.repostbot.service.conditions;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;

public interface PostCondition {

  Boolean test(SourceDomainId domainId, PostDTO post);
}
