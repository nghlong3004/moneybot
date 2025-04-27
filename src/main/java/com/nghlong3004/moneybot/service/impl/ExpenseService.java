package com.nghlong3004.moneybot.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.repository.IExpenseRepository;
import com.nghlong3004.moneybot.repository.jdbc.ExpenseRepository;
import com.nghlong3004.moneybot.service.IExpenseService;

public class ExpenseService implements IExpenseService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseService.class);
  private static ExpenseService INSTANCE;
  private final IExpenseRepository expenseRepository;

  public static synchronized ExpenseService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ExpenseService();
    }
    return INSTANCE;
  }

  private ExpenseService() {
    LOGGER.info("Initialized ExpenseService");
    expenseRepository = new ExpenseRepository();
  }


  @Override
  public boolean insertExpense(Expense expense) {
    LOGGER.info("ExpenseService: {}", "Insert expense");
    return expenseRepository.insertExpense(expense);
  }

  @Override
  public List<Expense> findExpensesToday(Long userId) {
    LOGGER.info("ExpenseService: {}", "Find expense today");
    return expenseRepository.findExpensesToday(userId);
  }

  @Override
  public List<Expense> findExpensesLastNDays(Long userId, int days) {
    LOGGER.info("ExpenseService: {}", "Find expense last n day");
    return expenseRepository.findExpensesLastNDays(userId, days);
  }

  @Override
  public List<Expense> findAllExpensesThisMonth(Long userId) {
    LOGGER.info("ExpenseService: {}", "Find all expenses this month");
    return expenseRepository.findAllExpensesThisMonth(userId);
  }

  @Override
  public boolean deleteExpenseById(Long expenseId) {
    LOGGER.info("ExpenseService: {}", "Delete expense by id");
    return expenseRepository.deleteExpenseById(expenseId);
  }

  @Override
  public long calculateTotalSpendingForToday(Long userId, String type) {
    LOGGER.info("ExpenseService: {}", "Calculate total spending for today");
    return expenseRepository.CalculateTotalSpendingForToday(userId, type);
  }

  @Override
  public long calculateTotalSpendingForNDays(Long userId, String type, int days) {
    LOGGER.info("ExpenseService: {}", "Calculate total spending for n days");
    return expenseRepository.CalculateTotalSpendingForNDays(userId, type, days);
  }

}
