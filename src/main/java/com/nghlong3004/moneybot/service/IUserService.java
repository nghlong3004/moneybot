package com.nghlong3004.moneybot.service;

import java.util.List;
import com.nghlong3004.moneybot.model.User;

public interface IUserService {
  
  boolean insertUser(User user);

  boolean updateUser(User user);

  List<User> searchUsersByKeyword(String keyword);

  boolean existsByTelegramUserId(Long telegramUserId);

  int countAllUsers();

  boolean deleteUser(Long telegramUserId);
}
