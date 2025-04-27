package com.nghlong3004.moneybot.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.repository.IExpenseRepository;
import com.nghlong3004.moneybot.repository.sql.ExpenseSQL;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;

public class ExpenseRepository implements IExpenseRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseRepository.class);

  public ExpenseRepository() {

  }

  @Override
  public boolean insertExpense(Expense expense) {
    String sql = ExpenseSQL.INSERT_EXPENSE;
    try (PreparedStatement stmt =
        ObjectContainerUtil.getDatabaseUtil().getConnection().prepareStatement(sql)) {
      stmt.setLong(1, expense.getUserId());
      stmt.setBigDecimal(2, expense.getAmount());
      stmt.setString(3, expense.getType());
      stmt.setString(4, expense.getDescription());
      boolean success = stmt.executeUpdate() > 0;
      LOGGER.info("Insert expense by [{}]: success = {}", expense.getUserId(), success);
      return success;
    } catch (SQLException e) {
      LOGGER.error("Failed to insert expense: {}", expense, e);
      throw new RuntimeException("Error inserting expense", e);
    }
  }

  @Override
  public long CalculateTotalSpendingForToday(Long userId, String type) {
    String sql = ExpenseSQL.CACULATE_TOTAL_SPENDING_FOR_TODAY;
    return CalculateTotalSpending(userId, type, sql);
  }

  @Override
  public long CalculateTotalSpendingForNDays(Long userId, String type, int days) {
    String sql = String.format(ExpenseSQL.CACULATE_TOTAL_SPENDING_FOR_N_DAY, days);
    return CalculateTotalSpending(userId, type, sql);
  }

  @Override
  public List<Expense> findExpensesToday(Long userId) {
    String sql = ExpenseSQL.FIND_EXPENSE_TODAY;
    return findExpenses(userId, sql);
  }

  @Override
  public List<Expense> findExpensesLastNDays(Long userId, int days) {
    String sql = String.format(ExpenseSQL.FIND_EXPENSE_N_DAY, days);
    return findExpenses(userId, sql);
  }

  @Override
  public List<Expense> findAllExpensesThisMonth(Long userId) {
    String sql = ExpenseSQL.FIND_EXPENSE_THIS_MONTH;
    return findExpenses(userId, sql);
  }

  @Override
  public boolean deleteExpenseById(Long expenseId) {
    String sql = ExpenseSQL.DELETE_EXPENSE_BY_ID;
    try (PreparedStatement stmt =
        ObjectContainerUtil.getDatabaseUtil().getConnection().prepareStatement(sql)) {
      stmt.setLong(1, expenseId);
      boolean success = stmt.executeUpdate() > 0;
      LOGGER.info("Delete expense id [{}]: success = {}", expenseId, success);
      return success;
    } catch (SQLException e) {
      LOGGER.error("Failed to delete expense id [{}]", expenseId, e);
      throw new RuntimeException("Error deleting expense", e);
    }
  }

  private long CalculateTotalSpending(Long userId, String type, String sql) {
    try (PreparedStatement stmt =
        ObjectContainerUtil.getDatabaseUtil().getConnection().prepareStatement(sql)) {
      stmt.setLong(1, userId);
      stmt.setString(2, type);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        long total = rs.getLong(1);
        LOGGER.info("Total Spending For Today: {}", total);
        return total;
      }
      return 0;
    } catch (SQLException e) {
      LOGGER.error("Failed to Calculate Total Spending For Today", e);
      throw new RuntimeException("Error Calculate Total Spending For Today", e);
    }
  }

  private List<Expense> findExpenses(Long userId, String sql) {
    try (PreparedStatement stmt =
        ObjectContainerUtil.getDatabaseUtil().getConnection().prepareStatement(sql)) {
      stmt.setLong(1, userId);
      try (ResultSet rs = stmt.executeQuery()) {
        List<Expense> expenses = new ArrayList<>();
        while (rs.next()) {
          expenses.add(mapToExpense(rs));
        }
        LOGGER.info("Found {} expenses for userId [{}]", expenses.size(), userId);
        return expenses;
      }
    } catch (SQLException e) {
      LOGGER.error("Failed to find expenses for userId [{}]", userId, e);
      throw new RuntimeException("Error finding expenses", e);
    }
  }

  private Expense mapToExpense(ResultSet rs) throws SQLException {
    return Expense.builder().id(rs.getLong("id")).userId(rs.getLong("user_id"))
        .amount(rs.getBigDecimal("amount")).type(rs.getString("type"))
        .description(rs.getString("description")).build();
  }


}
