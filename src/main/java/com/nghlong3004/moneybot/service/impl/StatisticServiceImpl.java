package com.nghlong3004.moneybot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nghlong3004.moneybot.service.ExpenseService;
import com.nghlong3004.moneybot.service.StatisticService;

public class StatisticServiceImpl implements StatisticService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StatisticServiceImpl.class);
  private final ExpenseService expenseService;

  public StatisticServiceImpl() {
    this.expenseService = ExpenseServiceImpl.getInstance();
    LOGGER.info("StatisticServiceImpl initialized.");
  }

  @Override
  public String getTodayStats(Long userId) {
    long income = expenseService.calculateTotalSpendingForToday(userId, "income");
    long expense = expenseService.calculateTotalSpendingForToday(userId, "expense");
    return String.format("Hôm nay bạn đã thu %s VND và chi %s VND.", income, expense);
  }

  @Override
  public String getLastNDaysStats(Long userId, int days) {
    long income = expenseService.calculateTotalSpendingForNDays(userId, "income", days);
    long expense = expenseService.calculateTotalSpendingForNDays(userId, "expense", days);
    return String.format("%d ngày gần đây bạn đã thu %s VND và chi %s VND.", days, income, expense);
  }

  @Override
  public String getThisWeekStats(Long userId) {
    long income = expenseService.calculateTotalSpendingForThisWeek(userId, "income");
    long expense = expenseService.calculateTotalSpendingForThisWeek(userId, "expense");
    return String.format("Tuần này bạn đã thu %s VND và chi %s VND.", income, expense);
  }

  @Override
  public String getThisMonthStats(Long userId) {
    long income = expenseService.calculateTotalSpendingForThisMonth(userId, "income");
    long expense = expenseService.calculateTotalSpendingForThisMonth(userId, "expense");
    return String.format("Tháng này bạn đã thu %s VND và chi %s VND.", income, expense);
  }

  @Override
  public String getLastMonthStats(Long userId) {
    long income = expenseService.calculateTotalSpendingForLastMonth(userId, "income");
    long expense = expenseService.calculateTotalSpendingForLastMonth(userId, "expense");
    return String.format("Tháng trước bạn đã thu %s VND và chi %s VND.", income, expense);
  }

  @Override
  public String getThisQuarterStats(Long userId) {
    long income = expenseService.calculateTotalSpendingForThisQuarter(userId, "income");
    long expense = expenseService.calculateTotalSpendingForThisQuarter(userId, "expense");
    return String.format("Quý này bạn đã thu %s VND và chi %s VND.", income, expense);
  }

  @Override
  public String getThisYearStats(Long userId) {
    long income = expenseService.calculateTotalSpendingForThisYear(userId, "income");
    long expense = expenseService.calculateTotalSpendingForThisYear(userId, "expense");
    return String.format("Năm nay bạn đã thu %s VND và chi %s VND.", income, expense);
  }
}
