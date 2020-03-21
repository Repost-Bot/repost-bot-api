package com.pluhin.repostbot.controller;

import static com.pluhin.repostbot.model.user.PrivilegeConstants.CREATE_POST;

import com.pluhin.repostbot.model.CreatePostDTO;
import com.pluhin.repostbot.service.CreatePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class CreatePostsController {

  private final CreatePostService createPostService;

  public CreatePostsController(CreatePostService createPostService) {
    this.createPostService = createPostService;
  }

  @Secured(CREATE_POST)
  @PostMapping
  public ResponseEntity<Void> createPost(@RequestBody CreatePostDTO createPostDTO) {
    createPostService.createPost(createPostDTO.getDomainId(), createPostDTO.getPost());
    return ResponseEntity.noContent().build();
  }
}
