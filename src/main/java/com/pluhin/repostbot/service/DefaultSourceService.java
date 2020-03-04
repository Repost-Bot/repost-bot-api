package com.pluhin.repostbot.service;

import com.pluhin.repostbot.entity.SourcesEntity;
import com.pluhin.repostbot.model.PostsSourceDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.repository.SourcesRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultSourceService implements SourceService {

  private final GetSourceInfoService getSourceInfoService;
  private final SourcesRepository sourcesRepository;

  public DefaultSourceService(GetSourceInfoService getSourceInfoService,
      SourcesRepository sourcesRepository) {
    this.getSourceInfoService = getSourceInfoService;
    this.sourcesRepository = sourcesRepository;
  }

  @Override
  public void createSource(SourceDomainId domainId) {
    PostsSourceDTO source = getSourceInfoService.getSource(domainId);
    SourcesEntity entity = new SourcesEntity();
    entity.setDomainId(source.getDomainId().getDomainId());
    entity.setDomainType(source.getDomainId().getDomainType());
    entity.setImageUrl(source.getImageUrl());
    entity.setName(source.getName());
    sourcesRepository.save(entity);
  }

  @Override
  public List<PostsSourceDTO> getAllSources() {
    return sourcesRepository.findAll()
        .stream()
        .map(entity -> new PostsSourceDTO(
            new SourceDomainId(entity.getDomainType(), entity.getDomainId()),
            entity.getImageUrl(),
            entity.getName()
        ))
        .collect(Collectors.toList());
  }

  @Override
  public void removeSource(SourceDomainId domainId) {
    sourcesRepository.deleteByDomainTypeAndDomainId(domainId.getDomainType(), domainId.getDomainId());
  }
}
