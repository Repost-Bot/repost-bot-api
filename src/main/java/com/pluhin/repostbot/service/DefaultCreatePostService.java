package com.pluhin.repostbot.service;

import com.pluhin.repostbot.bot.RepostBot;
import com.pluhin.repostbot.model.PostDTO;
import com.pluhin.repostbot.model.PostStatus;
import com.pluhin.repostbot.model.domainid.SourceDomainId;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCreatePostService implements CreatePostService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCreatePostService.class);

  private final RepostBot repostBot;
  private final PostsHistoryService postsHistoryService;

  public DefaultCreatePostService(RepostBot repostBot, PostsHistoryService postsHistoryService) {
    this.repostBot = repostBot;
    this.postsHistoryService = postsHistoryService;
  }

  @Override
  public void createPost(SourceDomainId domainId, PostDTO post) {
    List<File> files = new ArrayList<>();
    try {
      files = post.getImages()
          .stream()
          .map(this::getFileFromUrl)
          .collect(Collectors.toList());
      repostBot.sendPost(files, post.getText());
      postsHistoryService.savePost(domainId, post, PostStatus.DELIVERED);
    } catch (Exception ex) {
      postsHistoryService.savePost(domainId, post, PostStatus.ERROR);
      throw ex;
    } finally {
      files.forEach(File::delete);
    }
  }

  private File getFileFromUrl(String fileUrl) {
    LOGGER.info("Getting file from url {}", fileUrl);
    String tempFileName = UUID.randomUUID().toString();
    try {
      URL url = new URL(fileUrl);
      ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
      FileOutputStream fileOutputStream = new FileOutputStream(tempFileName);
      fileOutputStream.getChannel()
          .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
      return new File(tempFileName);
    } catch (Exception ex) {
      LOGGER.error("Cannot get file from url", ex);
      throw new RuntimeException(ex);
    }
  }
}