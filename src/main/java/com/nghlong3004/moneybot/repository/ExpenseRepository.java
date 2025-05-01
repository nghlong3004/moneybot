package com.nghlong3004.moneybot.repository;

import com.nghlong3004.moneybot.model.Expense;


public interface ExpenseRepository {

  boolean insertExpense(Expense expense);

  boolean deleteExpenseById(Long expenseId);

  long calculateTotalSpendingForToday(Long userId, String type);

  long calculateTotalSpendingForNDays(Long userId, String type, int days);

  long calculateTotalSpendingForThisWeek(Long userId, String type);

  long calculateTotalSpendingForThisMonth(Long userId, String type);

  long calculateTotalSpendingForLastMonth(Long userId, String type);

  long calculateTotalSpendingForThisQuarter(Long userId, String type);

  long calculateTotalSpendingForThisYear(Long userId, String type);

  int countTransactionsByUser(Long userId);
}

