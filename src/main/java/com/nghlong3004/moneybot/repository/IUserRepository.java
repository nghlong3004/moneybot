package com.nghlong3004.moneybot.repository;

import java.util.List;
import com.nghlong3004.moneybot.model.User;

public interface IUserRepository {
  
  boolean insertUser(User user);

  boolean updateUser(User user);

  List<User> searchUsersByKeyword(String keyword);

  boolean existsByTelegramUserId(Long telegramUserId);

  int countAllUsers();

  boolean deleteUser(Long telegramUserId);
}
