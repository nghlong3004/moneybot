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

  public static final String CACULATE_TOTAL_SPENDING_FOR_TODAY =
      "SELECT SUM(amount) FROM expense WHERE user_id = ? AND type = ? AND date(created_at) = current_date";

  public static final String CACULATE_TOTAL_SPENDING_FOR_N_DAY =
      "SELECT SUM(amount) FROM expense WHERE user_id = ? AND type = ? AND created_at >= CURRENT_DATE - INTERVAL '%s days'";

}
