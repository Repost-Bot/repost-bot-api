package com.pluhin.repostbot.repository;

import com.pluhin.repostbot.entity.BotNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotNotificationRepository extends JpaRepository<BotNotificationEntity, Long> {

}
