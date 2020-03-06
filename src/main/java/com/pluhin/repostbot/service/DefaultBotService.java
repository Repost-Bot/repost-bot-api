package com.pluhin.repostbot.service;

import com.pluhin.repostbot.bot.RepostBot;
import com.pluhin.repostbot.entity.AdminsEntity;
import com.pluhin.repostbot.repository.AdminsRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultBotService implements BotService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBotService.class);

  private final AdminsRepository adminsRepository;
  private final RepostBot repostBot;

  public DefaultBotService(AdminsRepository adminsRepository, RepostBot repostBot) {
    this.adminsRepository = adminsRepository;
    this.repostBot = repostBot;
  }

  @Override
  public void sendPost(String image, String text) {
    File file = getFileFromUrl(image);
    try {
      LOGGER.info("Sending post with one image");
      repostBot.sendPost(file, text);
    } finally {
      file.delete();
    }
  }

  @Override
  public void sendPost(List<String> images, String text) {
    List<File> files = images
        .stream()
        .map(this::getFileFromUrl)
        .collect(Collectors.toList());

    try {
      LOGGER.info("Sending post with {} images", images.size());
      repostBot.sendPost(files, text);
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
      List<Long> admins = adminsRepository
          .findAll()
          .stream()
          .map(AdminsEntity::getTelegramId)
          .collect(Collectors.toList());

      repostBot.sendMessage(admins, "Cannot get image by url. See logs for detailed error");
      throw new RuntimeException(ex);
    }
  }
}
