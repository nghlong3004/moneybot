package com.nghlong3004.moneybot.service.impl;

import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.repository.ExpenseRepository;
import com.nghlong3004.moneybot.repository.jdbc.ExpenseRepositoryImpl;
import com.nghlong3004.moneybot.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpenseServiceImpl implements ExpenseService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseServiceImpl.class);
  private static ExpenseServiceImpl INSTANCE;
  private final ExpenseRepository expenseRepository;

  private ExpenseServiceImpl() {
    LOGGER.info("Initialized ExpenseService");
    this.expenseRepository = new ExpenseRepositoryImpl();
  }

  public static synchronized ExpenseServiceImpl getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ExpenseServiceImpl();
    }
    return INSTANCE;
  }

  @Override
  public boolean insertExpense(Expense expense) {
    LOGGER.info("Insert expense");
    return expenseRepository.insertExpense(expense);
  }

  @Override
  public boolean deleteExpenseById(Long expenseId) {
    LOGGER.info("Delete expense by id");
    return expenseRepository.deleteExpenseById(expenseId);
  }


  @Override
  public long calculateTotalSpendingForToday(Long userId, String type) {
    LOGGER.info("Calculate total spending for today");
    return expenseRepository.calculateTotalSpendingForToday(userId, type);
  }

  @Override
  public long calculateTotalSpendingForNDays(Long userId, String type, int days) {
    LOGGER.info("Calculate total spending for last {} days", days);
    return expenseRepository.calculateTotalSpendingForNDays(userId, type, days);
  }

  @Override
  public long calculateTotalSpendingForThisWeek(Long userId, String type) {
    LOGGER.info("Calculate total spending for this week");
    return expenseRepository.calculateTotalSpendingForThisWeek(userId, type);
  }

  @Override
  public long calculateTotalSpendingForThisMonth(Long userId, String type) {
    LOGGER.info("Calculate total spending for this month");
    return expenseRepository.calculateTotalSpendingForThisMonth(userId, type);
  }

  @Override
  public long calculateTotalSpendingForLastMonth(Long userId, String type) {
    LOGGER.info("Calculate total spending for last month");
    return expenseRepository.calculateTotalSpendingForLastMonth(userId, type);
  }

  @Override
  public long calculateTotalSpendingForThisQuarter(Long userId, String type) {
    LOGGER.info("Calculate total spending for this quarter");
    return expenseRepository.calculateTotalSpendingForThisQuarter(userId, type);
  }

  @Override
  public long calculateTotalSpendingForThisYear(Long userId, String type) {
    LOGGER.info("Calculate total spending for this year");
    return expenseRepository.calculateTotalSpendingForThisYear(userId, type);
  }

  @Override
  public int countTransactionsByUser(Long telegramUserId) {
    LOGGER.info("Count transactions by user");
    return expenseRepository.countTransactionsByUser(telegramUserId);
  }
}
