package com.pluhin.repostbot.model;

import java.time.LocalDate;
import java.util.Map;

public class DateStatsDTO {

  private final Map<LocalDate, StatsDTO> dailyStats;

  public DateStatsDTO(Map<LocalDate, StatsDTO> dailyStats) {
    this.dailyStats = dailyStats;
  }

  public Map<LocalDate, StatsDTO> getDailyStats() {
    return dailyStats;
  }
}
