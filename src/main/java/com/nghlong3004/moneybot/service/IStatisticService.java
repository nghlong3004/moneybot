package com.nghlong3004.moneybot.service;

public interface IStatisticService {
  String getTodayStats(Long userId);

  String getLastNDaysStats(Long userId, int days);

  String getThisWeekStats(Long userId);

  String getThisMonthStats(Long userId);

  String getLastMonthStats(Long userId);

  String getThisQuarterStats(Long userId);

  String getThisYearStats(Long userId);
}
