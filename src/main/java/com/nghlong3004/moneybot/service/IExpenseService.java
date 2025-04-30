package com.nghlong3004.moneybot.service;

import com.nghlong3004.moneybot.model.Expense;

public interface IExpenseService {

  boolean insertExpense(Expense expense);

  boolean deleteExpenseById(Long expenseId);

  long calculateTotalSpendingForToday(Long userId, String type);

  long calculateTotalSpendingForNDays(Long userId, String type, int days);

  long calculateTotalSpendingForThisWeek(Long userId, String type);

  long calculateTotalSpendingForThisMonth(Long userId, String type);

  long calculateTotalSpendingForLastMonth(Long userId, String type);

  long calculateTotalSpendingForThisQuarter(Long userId, String type);

  long calculateTotalSpendingForThisYear(Long userId, String type);

  int countTransactionsByUser(Long telegramUserId);
}
