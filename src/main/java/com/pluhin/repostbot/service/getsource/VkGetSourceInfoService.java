package com.pluhin.repostbot.service.getsource;

import com.pluhin.repostbot.exception.GetSourceInfoException;
import com.pluhin.repostbot.model.PostsSourceDTO;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VkGetSourceInfoService implements GetSourceInfoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(VkGetSourceInfoService.class);

  private final VkApiClient client;
  private final ServiceActor actor;

  public VkGetSourceInfoService(VkApiClient client, ServiceActor actor) {
    this.client = client;
    this.actor = actor;
  }

  @Override
  public PostsSourceDTO getSource(SourceDomainId domainId) {
    try {
      return client.groups().getById(actor)
          .groupId(domainId.getDomainId())
          .execute()
          .stream()
          .findFirst()
          .map(group -> new PostsSourceDTO(
              domainId,
              getUrl(group),
              group.getName()
          ))
          .orElse(null);
    } catch (ApiException e) {
      LOGGER.error("Cannot get vk group by id {}", domainId.getDomainId(), e);
      throw new GetSourceInfoException(e);
    } catch (ClientException e) {
      LOGGER.error("Cannot get vk group by id {}", domainId.getDomainId(), e);
      throw new GetSourceInfoException(e);
    }
  }

  private String getUrl(GroupFull groupFull) {
    if (groupFull.getPhoto200() != null) {
      return groupFull.getPhoto200();
    }
    if (groupFull.getPhoto100() != null) {
      return groupFull.getPhoto100();
    }
    return groupFull.getPhoto50();
  }
}
