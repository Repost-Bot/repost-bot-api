package com.pluhin.repostbot.service.getsource;

import com.pluhin.repostbot.model.PostsSourceDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DefaultGetSourceInfoService implements GetSourceInfoService {

  private final Map<SourceDomainType, GetSourceInfoService> dictionary;

  private DefaultGetSourceInfoService(
      Map<SourceDomainType, GetSourceInfoService> dictionary) {
    this.dictionary = dictionary;
  }

  @Override
  public PostsSourceDTO getSource(SourceDomainId domainId) {
    return dictionary.get(domainId.getDomainType()).getSource(domainId);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Map<SourceDomainType, GetSourceInfoService> dictionary = new EnumMap(SourceDomainType.class);

    public Builder put(SourceDomainType type, GetSourceInfoService service) {
      dictionary.put(type, service);
      return this;
    }

    public GetSourceInfoService build() {
      return new DefaultGetSourceInfoService(dictionary);
    }
  }
}
