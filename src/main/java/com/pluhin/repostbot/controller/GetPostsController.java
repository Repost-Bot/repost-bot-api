package com.pluhin.repostbot.controller;

import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.model.domainid.SourceDomainType;
import com.pluhin.repostbot.service.getposts.GetPostsService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class GetPostsController {

  private final GetPostsService getPostsService;

  public GetPostsController(GetPostsService getPostsService) {
    this.getPostsService = getPostsService;
  }

  @GetMapping("/{domainType}/{domainId}")
  public List<PostDTO> getPosts(
      @PathVariable SourceDomainType domainType,
      @PathVariable String domainId,
      @RequestParam(required = false) Long count,
      @RequestParam(required = false) Long offset
  ) {
    SourceDomainId sourceDomainId = new SourceDomainId(domainType, domainId);
    return getPostsService.getPosts(sourceDomainId, count, offset);
  }
}
