package com.pluhin.repostbot.model;

public class StatsDTO {

  private final Long totalDelivered;
  private final Long totalDeclined;

  public StatsDTO(Long totalDelivered, Long totalDeclined) {
    this.totalDelivered = totalDelivered;
    this.totalDeclined = totalDeclined;
  }

  public Long getTotalDelivered() {
    return totalDelivered;
  }

  public Long getTotalDeclined() {
    return totalDeclined;
  }
}
