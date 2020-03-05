package com.pluhin.repostbot.controller;

import com.pluhin.repostbot.model.StatsDTO;
import com.pluhin.repostbot.service.stats.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  private final StatsService statsService;

  public HomeController(StatsService statsService) {
    this.statsService = statsService;
  }

  @GetMapping
  public StatsDTO getStats() {
    return statsService.getStats();
  }
}
