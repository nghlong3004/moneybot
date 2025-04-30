package com.nghlong3004.moneybot.repository.sql;

public final class ExpenseSQL {

  public static final String INSERT_EXPENSE =
      "INSERT INTO expense (user_id, amount, type, description) VALUES (?, ?, ?, ?)";

  public static final String FIND_EXPENSE_TODAY =
      "SELECT * FROM expense WHERE user_id = ? AND date(created_at) = current_date";

  public static final String FIND_EXPENSE_N_DAY =
      "SELECT * FROM expense WHERE user_id = ? AND created_at >= CURRENT_DATE - INTERVAL '%s days'";

  public static final String FIND_EXPENSE_THIS_MONTH =
      "SELECT * FROM expense WHERE user_id = ? AND date_trunc('month', created_at) = date_trunc('month', current_date)";

  public static final String DELETE_EXPENSE_BY_ID = "DELETE FROM expense WHERE id = ?";

  public static final String CALCULATE_TOTAL_SPENDING_FOR_TODAY =
      "SELECT COALESCE(SUM(amount), 0) FROM expense WHERE user_id = ? AND type = ? AND date(created_at) = current_date";

  public static final String CALCULATE_TOTAL_SPENDING_FOR_N_DAY =
      "SELECT COALESCE(SUM(amount), 0) FROM expense WHERE user_id = ? AND type = ? AND created_at >= CURRENT_DATE - INTERVAL '%s days'";

  public static final String CALCULATE_TOTAL_SPENDING_THIS_WEEK =
      "SELECT COALESCE(SUM(amount), 0) FROM expense WHERE user_id = ? AND type = ? AND date_trunc('week', created_at) = date_trunc('week', current_date)";

  public static final String CALCULATE_TOTAL_SPENDING_THIS_MONTH =
      "SELECT COALESCE(SUM(amount), 0) FROM expense WHERE user_id = ? AND type = ? AND date_trunc('month', created_at) = date_trunc('month', current_date)";

  public static final String CALCULATE_TOTAL_SPENDING_LAST_MONTH =
      "SELECT COALESCE(SUM(amount), 0) FROM expense WHERE user_id = ? AND type = ? AND created_at >= date_trunc('month', current_date) - INTERVAL '1 month' AND created_at < date_trunc('month', current_date)";

  public static final String CALCULATE_TOTAL_SPENDING_THIS_QUARTER =
      "SELECT COALESCE(SUM(amount), 0) FROM expense WHERE user_id = ? AND type = ? AND date_trunc('quarter', created_at) = date_trunc('quarter', current_date)";

  public static final String CALCULATE_TOTAL_SPENDING_THIS_YEAR =
      "SELECT COALESCE(SUM(amount), 0) FROM expense WHERE user_id = ? AND type = ? AND date_trunc('year', created_at) = date_trunc('year', current_date)";

  public static final String COUNT_EXPENSE_BY_USER =
      "SELECT COUNT(*) FROM expense WHERE user_id = ?";
}
