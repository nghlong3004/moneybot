package com.nghlong3004.moneybot.repository.sql;

public final class UserSQL {

  public static final String INSERT_USER =
      "INSERT INTO telegram_user (telegram_user_id, username, first_name, last_name) VALUES (?, ?, ?, ?)";

  public static final String UPDATE_USER =
      "UPDATE telegram_user SET username = ?, first_name = ?, last_name = ? WHERE telegram_user_id = ?";

  public static final String SEARCH_USERS_BY_KEYWORD =
      "SELECT * FROM telegram_user WHERE username ILIKE ? OR first_name ILIKE ? OR last_name ILIKE ?";

  public static final String EXITS_BY_TELEGRAM_USER_ID =
      "SELECT 1 FROM telegram_user WHERE telegram_user_id = ?";

  public static final String COUNT_ALL_USER = "SELECT COUNT(*) FROM telegram_user";

  public static final String DELETE_USER = "DELETE FROM telegram_user WHERE telegram_user_id = ?";

}
