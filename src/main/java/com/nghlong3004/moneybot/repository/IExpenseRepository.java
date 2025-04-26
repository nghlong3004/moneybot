package com.nghlong3004.moneybot.repository;

import java.util.List;
import com.nghlong3004.moneybot.model.Expense;

public interface IExpenseRepository {
  
  boolean insertExpense(Expense expense);

  List<Expense> findExpensesToday(Long userId);

  List<Expense> findExpensesLastNDays(Long userId, int days);

  List<Expense> findAllExpensesThisMonth(Long userId);

  boolean deleteExpenseById(Long expenseId);
}
