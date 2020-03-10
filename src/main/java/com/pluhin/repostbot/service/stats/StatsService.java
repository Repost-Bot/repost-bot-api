package com.pluhin.repostbot.service.stats;

import com.pluhin.repostbot.model.DateStatsDTO;
import com.pluhin.repostbot.model.StatsDTO;
import java.time.LocalDateTime;

public interface StatsService {

  StatsDTO getStats();

  DateStatsDTO getDailyStats(LocalDateTime rangeFrom, LocalDateTime rangeTo);
}
