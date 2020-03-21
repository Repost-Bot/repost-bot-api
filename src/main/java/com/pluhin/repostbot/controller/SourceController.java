package com.pluhin.repostbot.controller;

import static com.pluhin.repostbot.model.user.PrivilegeConstants.ADD_SOURCE;
import static com.pluhin.repostbot.model.user.PrivilegeConstants.GET_ALL_SOURCES;
import static com.pluhin.repostbot.model.user.PrivilegeConstants.REMOVE_SOURCE;

import com.pluhin.repostbot.model.PostsSourceDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.model.user.PrivilegeConstants;
import com.pluhin.repostbot.service.SourceService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sources")
public class SourceController {

  private final SourceService sourceService;

  public SourceController(SourceService sourceService) {
    this.sourceService = sourceService;
  }

  @Secured(ADD_SOURCE)
  @PostMapping
  public ResponseEntity<Void> createSource(@RequestBody SourceDomainId domainId) {
    sourceService.createSource(domainId);
    return ResponseEntity.noContent().build();
  }

  @Secured(GET_ALL_SOURCES)
  @GetMapping
  public List<PostsSourceDTO> getPostsSources() {
    return sourceService.getAllSources();
  }

  @Secured(REMOVE_SOURCE)
  @DeleteMapping
  public ResponseEntity<Void> removeSource(@RequestBody SourceDomainId domainId) {
    sourceService.removeSource(domainId);
    return ResponseEntity.noContent().build();
  }
}
