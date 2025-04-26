package com.nghlong3004.moneybot.repository.jdbc;

import java.util.List;
import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.repository.IExpenseRepository;

public class ExpenseRepository implements IExpenseRepository {

  @Override
  public boolean insertExpense(Expense expense) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<Expense> findExpensesToday(Long userId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Expense> findExpensesLastNDays(Long userId, int days) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Expense> findAllExpensesThisMonth(Long userId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean deleteExpenseById(Long expenseId) {
    // TODO Auto-generated method stub
    return false;
  }

}
