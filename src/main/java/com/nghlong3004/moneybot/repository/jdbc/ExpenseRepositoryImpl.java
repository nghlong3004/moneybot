package com.nghlong3004.moneybot.repository.jdbc;

import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.repository.ExpenseRepository;
import com.nghlong3004.moneybot.repository.sql.ExpenseSQL;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ExpenseRepositoryImpl implements ExpenseRepository{

  private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseRepositoryImpl.class);

  public ExpenseRepositoryImpl() {}

  @Override
  public boolean insertExpense(Expense expense) {
    String sql = ExpenseSQL.INSERT_EXPENSE;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, expense.getUserId());
      stmt.setBigDecimal(2, expense.getAmount());
      stmt.setString(3, expense.getType());
      stmt.setString(4, expense.getDescription());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      LOGGER.error("Failed to insert expense: {}", expense, e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean deleteExpenseById(Long expenseId) {
    String sql = ExpenseSQL.DELETE_EXPENSE_BY_ID;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, expenseId);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      LOGGER.error("Failed to delete expense id [{}]", expenseId, e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public long calculateTotalSpendingForToday(Long userId, String type) {
    return calculateTotalSpending(userId, type, ExpenseSQL.CALCULATE_TOTAL_SPENDING_FOR_TODAY);
  }

  @Override
  public long calculateTotalSpendingForNDays(Long userId, String type, int days) {
    return calculateTotalSpending(userId, type,
        String.format(ExpenseSQL.CALCULATE_TOTAL_SPENDING_FOR_N_DAY, days));
  }

  @Override
  public long calculateTotalSpendingForThisWeek(Long userId, String type) {
    return calculateTotalSpending(userId, type, ExpenseSQL.CALCULATE_TOTAL_SPENDING_THIS_WEEK);
  }

  @Override
  public long calculateTotalSpendingForThisMonth(Long userId, String type) {
    return calculateTotalSpending(userId, type, ExpenseSQL.CALCULATE_TOTAL_SPENDING_THIS_MONTH);
  }

  @Override
  public long calculateTotalSpendingForLastMonth(Long userId, String type) {
    return calculateTotalSpending(userId, type, ExpenseSQL.CALCULATE_TOTAL_SPENDING_LAST_MONTH);
  }

  @Override
  public long calculateTotalSpendingForThisQuarter(Long userId, String type) {
    return calculateTotalSpending(userId, type, ExpenseSQL.CALCULATE_TOTAL_SPENDING_THIS_QUARTER);
  }

  @Override
  public long calculateTotalSpendingForThisYear(Long userId, String type) {
    return calculateTotalSpending(userId, type, ExpenseSQL.CALCULATE_TOTAL_SPENDING_THIS_YEAR);
  }

  @Override
  public int countTransactionsByUser(Long userId) {
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(ExpenseSQL.COUNT_EXPENSE_BY_USER)) {
      stmt.setLong(1, userId);
      ResultSet rs = stmt.executeQuery();
      return rs.next() ? rs.getInt(1) : 0;
    } catch (SQLException e) {
      LOGGER.error("Failed to count transactions for userId={}", userId, e);
      throw new RuntimeException(e);
    }
  }

  private long calculateTotalSpending(Long userId, String type, String sql) {
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, userId);
      stmt.setString(2, type);
      ResultSet rs = stmt.executeQuery();
      return rs.next() ? rs.getLong(1) : 0;
    } catch (SQLException e) {
      LOGGER.error("Failed to calculate total spending", e);
      throw new RuntimeException(e);
    }
  }

}
