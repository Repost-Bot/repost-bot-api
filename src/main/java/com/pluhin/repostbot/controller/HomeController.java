package com.pluhin.repostbot.controller;

import com.pluhin.repostbot.model.DateStatsDTO;
import com.pluhin.repostbot.model.StatsDTO;
import com.pluhin.repostbot.service.stats.StatsService;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  private final StatsService statsService;

  public HomeController(StatsService statsService) {
    this.statsService = statsService;
  }

  @GetMapping("/")
  public StatsDTO getStats() {
    return statsService.getStats();
  }

  @GetMapping("/stats")
  public DateStatsDTO getDailyStats(
      @RequestParam
      @DateTimeFormat(iso = ISO.DATE_TIME)
          LocalDateTime rangeFrom,
      @RequestParam
      @DateTimeFormat(iso = ISO.DATE_TIME)
          LocalDateTime rangeTo
  ) {
    return statsService.getDailyStats(rangeFrom, rangeTo);
  }
}
