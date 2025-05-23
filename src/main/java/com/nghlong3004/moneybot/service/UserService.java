package com.nghlong3004.moneybot.service;

import java.util.List;
import com.nghlong3004.moneybot.constant.SpreadsheetLinkStatus;
import com.nghlong3004.moneybot.model.User;

public interface UserService {

  boolean insertUser(User user);

  boolean updateUser(User user);

  List<User> searchUsersByKeyword(String keyword);

  boolean existsByTelegramUserId(Long telegramUserId);

  int countAllUsers();

  boolean deleteUser(Long telegramUserId);
  
  boolean hasSpreadsheetId(Long telegramUserId);
  
  boolean updateSpreadsheetId(Long telegramUserId, String spreadsheetId);
  
  String getSpreadsheetId(Long telegramUserId);
  
  boolean updateSpreadsheetStatus(Long telegramUserId, SpreadsheetLinkStatus status);
  
  User findUserByTelegramId(Long telegramUserId);
  
}
