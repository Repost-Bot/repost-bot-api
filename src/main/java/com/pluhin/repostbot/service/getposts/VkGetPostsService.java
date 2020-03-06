package com.pluhin.repostbot.service.getposts;

import com.pluhin.repostbot.exception.GetPostsException;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.pluhin.repostbot.service.PostsHistoryService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VkGetPostsService implements GetPostsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(VkGetPostsService.class);

  private final VkApiClient client;
  private final ServiceActor actor;
  private final PostsHistoryService postsHistoryService;

  public VkGetPostsService(VkApiClient client, ServiceActor actor,
      PostsHistoryService postsHistoryService) {
    this.client = client;
    this.actor = actor;
    this.postsHistoryService = postsHistoryService;
  }

  @Override
  public List<PostDTO> getPosts(SourceDomainId domainId, Long count, Long offset) {
    Integer ownerId = Integer.parseInt(domainId.getDomainId());
    List<Long> historyIds = postsHistoryService.getSourceIdsFromHistory(domainId);
    Integer finalCount = getCount(count, historyIds).intValue();
    Integer finalOffset = getOffset(offset, historyIds).intValue();
    try {
      return client.wall().get(actor)
          .ownerId(-ownerId)
          .count(finalCount)
          .offset(finalOffset)
          .execute()
          .getItems()
          .stream()
          .filter(x -> !historyIds.contains(x.getId().longValue()))
          .map(post -> new PostDTO(
                  post.getId().longValue(),
                  getAttachments(post),
                  post.getText()
              )
          )
          .limit(count)
          .collect(Collectors.toList());
    } catch (ApiException e) {
      LOGGER.error("Error fetching posts via api", e);
      throw new GetPostsException(e);
    } catch (ClientException e) {
      LOGGER.error("Client exception", e);
      throw new GetPostsException(e);
    }
  }

  private List<String> getAttachments(WallPostFull post) {
    return Optional.ofNullable(post.getAttachments())
        .orElse(Collections.emptyList())
        .stream()
        .map(WallpostAttachment::getPhoto)
        .filter(Objects::nonNull)
        .map(this::getUrl)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private String getUrl(Photo photo) {
    if (photo.getPhoto807() != null) {
      return photo.getPhoto807();
    } else if (photo.getPhoto604() != null) {
      return photo.getPhoto604();
    } else {
      return null;
    }
  }

  private Long getCount(Long requestedCount, List<Long> history) {
    if (requestedCount + history.size() > 100) {
      return 100L;
    } else {
      return requestedCount + history.size();
    }
  }

  private Long getOffset(Long requestedOffset, List<Long> history) {
    if (history.size() > 100) {
      return history.size() - 100L;
    } else {
      return requestedOffset;
    }
  }
}
