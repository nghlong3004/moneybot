package com.nghlong3004.moneybot.service;

import java.util.List;
import com.nghlong3004.moneybot.model.Expense;

public interface IExpenseService {

  boolean insertExpense(Expense expense);

  long calculateTotalSpendingForToday(Long userId, String type);

  long calculateTotalSpendingForNDays(Long userId, String type, int days);

  List<Expense> findExpensesToday(Long userId);

  List<Expense> findExpensesLastNDays(Long userId, int days);

  List<Expense> findAllExpensesThisMonth(Long userId);

  boolean deleteExpenseById(Long expenseId);

}
