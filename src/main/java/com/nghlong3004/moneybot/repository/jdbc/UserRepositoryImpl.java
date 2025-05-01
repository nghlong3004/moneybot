package com.nghlong3004.moneybot.repository.jdbc;

import com.nghlong3004.moneybot.constant.SpreadsheetLinkStatus;
import com.nghlong3004.moneybot.model.User;
import com.nghlong3004.moneybot.repository.UserRepository;
import com.nghlong3004.moneybot.repository.sql.UserSQL;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRepositoryImpl implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

  public UserRepositoryImpl() {

  }

  @Override
  public boolean insertUser(User user) {
    String sql = UserSQL.INSERT_USER;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, user.getTelegramUserId());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getFirstName());
      stmt.setString(4, user.getLastName());
      stmt.setString(5, user.getSpreadsheetId());
      stmt.setString(6, user.getSpreadsheetStatus().name());
      boolean success = stmt.executeUpdate() > 0;
      LOGGER.info("Insert user [{}]: success = {}", user, success);
      return success;
    } catch (SQLException e) {
      LOGGER.error("Failed to insert user: {}", user, e);
      throw new RuntimeException("Error inserting user", e);
    }
  }

  @Override
  public boolean updateUser(User user) {
    String sql = UserSQL.UPDATE_USER;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getFirstName());
      stmt.setString(3, user.getLastName());
      stmt.setLong(4, user.getTelegramUserId());
      stmt.setString(5, user.getSpreadsheetId());
      stmt.setString(6, user.getSpreadsheetStatus().name());
      boolean success = stmt.executeUpdate() > 0;
      LOGGER.info("Update user [{}]: success = {}", user, success);
      return success;
    } catch (SQLException e) {
      LOGGER.error("Failed to update user: {}", user, e);
      throw new RuntimeException("Error updating user", e);
    }
  }

  @Override
  public List<User> searchUsersByKeyword(String keyword) {
    String sql = UserSQL.SEARCH_USERS_BY_KEYWORD;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      String likeKeyword = "%" + keyword + "%";
      stmt.setString(1, likeKeyword);
      stmt.setString(2, likeKeyword);
      stmt.setString(3, likeKeyword);
      try (ResultSet rs = stmt.executeQuery()) {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
          users.add(mapToUser(rs));
        }
        LOGGER.info("Found {} users matching keyword [{}]", users.size(), keyword);
        return users;
      }
    } catch (SQLException e) {
      LOGGER.error("Failed to search users with keyword [{}]", keyword, e);
      throw new RuntimeException("Error searching users", e);
    }
  }

  @Override
  public boolean existsByTelegramUserId(Long telegramUserId) {
    String sql = UserSQL.EXITS_BY_TELEGRAM_USER_ID;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, telegramUserId);
      try (ResultSet rs = stmt.executeQuery()) {
        boolean exists = rs.next();
        LOGGER.info("Check exists telegramUserId [{}]: {}", telegramUserId, exists);
        return exists;
      }
    } catch (SQLException e) {
      LOGGER.error("Failed to check existence of userId [{}]", telegramUserId, e);
      throw new RuntimeException("Error checking user existence", e);
    }
  }

  @Override
  public int countAllUsers() {
    String sql = UserSQL.COUNT_ALL_USER;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      if (rs.next()) {
        int count = rs.getInt(1);
        LOGGER.info("Total users counted: {}", count);
        return count;
      }
      return 0;
    } catch (SQLException e) {
      LOGGER.error("Failed to count users", e);
      throw new RuntimeException("Error counting users", e);
    }
  }

  @Override
  public boolean deleteUser(Long telegramUserId) {
    String sql = UserSQL.DELETE_USER;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, telegramUserId);
      boolean success = stmt.executeUpdate() > 0;
      LOGGER.info("Delete user [{}]: success = {}", telegramUserId, success);
      return success;
    } catch (SQLException e) {
      LOGGER.error("Failed to delete user [{}]", telegramUserId, e);
      throw new RuntimeException("Error deleting user", e);
    }
  }

  @Override
  public boolean hasSpreadsheetId(Long telegramUserId) {
    String sql = UserSQL.HAS_SPREAD_SHEET_ID;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, telegramUserId);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      LOGGER.error("Error has spreadsheet_id: ", e);
      return false;
    }
  }

  @Override
  public String getSpreadsheetId(Long telegramUserId) {
    String sql = UserSQL.GET_SPREAD_SHEET_ID;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, telegramUserId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getString("spreadsheet_id");
        }
      }
    } catch (SQLException e) {
      LOGGER.error("[UserRepository] Lỗi khi lấy spreadsheet_id: ", e);
    }
    return null;
  }

  @Override
  public boolean updateSpreadsheetStatus(Long telegramUserId, SpreadsheetLinkStatus status) {
    String sql = UserSQL.UPDATE_SPREAD_SHEET_STATUS;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, status.name());
      stmt.setLong(2, telegramUserId);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      LOGGER.error("Error for update spreadsheet_status: ", e);
      return false;
    }
  }

  @Override
  public User findUserByTelegramId(Long telegramUserId) {
    String sql = UserSQL.FIND_USER_BY_TELEGRAM_ID;
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, telegramUserId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          User user = mapToUser(rs);
          return user;
        }
      }
    } catch (SQLException e) {
      LOGGER.error("Error find user for DB: ", e);
    }
    return null;
  }

  @Override
  public boolean updateSpreadsheetId(Long telegramUserId, String spreadsheetId) {
    String sql = "UPDATE telegram_user SET spreadsheet_id = ? WHERE telegram_user_id = ?";
    try (Connection conn = ObjectContainerUtil.getDatabaseUtil().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, spreadsheetId);
      stmt.setLong(2, telegramUserId);

      boolean success = stmt.executeUpdate() > 0;
      LOGGER.info("Update spreadsheet_id for userId [{}]: success = {}", telegramUserId, success);
      return success;

    } catch (SQLException e) {
      LOGGER.error("Error spreadsheet_id for userId [{}]: {}", telegramUserId, e.getMessage(), e);
      return false;
    }
  }


  private User mapToUser(ResultSet rs) throws SQLException {
    User user = new User();
    user.setTelegramUserId(rs.getLong("telegram_user_id"));
    user.setUsername(rs.getString("username"));
    user.setFirstName(rs.getString("first_name"));
    user.setLastName(rs.getString("last_name"));
    user.setSpreadsheetId(rs.getString("spreadsheet_id"));

    String statusStr = rs.getString("spreadsheet_status");
    try {
      user.setSpreadsheetStatus(SpreadsheetLinkStatus.valueOf(statusStr));
    } catch (IllegalArgumentException e) {
      LOGGER.warn("fallback spreadsheet_status [{}] for enum", statusStr);
      user.setSpreadsheetStatus(SpreadsheetLinkStatus.NOT_LINKED);
    }


    return user;
  }

}
