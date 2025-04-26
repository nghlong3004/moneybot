package com.nghlong3004.moneybot.repository;

import java.util.List;
import java.util.Optional;
import com.nghlong3004.moneybot.model.User;

public interface IUserRepository {
  
  boolean insertUser(User user);

  Optional<User> findByTelegramUserId(Long telegramUserId);

  boolean updateUser(User user);

  boolean deactivateUser(Long telegramUserId);

  List<User> findAllActiveUsers();

  List<User> searchUsersByKeyword(String keyword);

  boolean existsByTelegramUserId(Long telegramUserId);

  int countAllUsers();

  int countActiveUsers();

  boolean deleteUser(Long telegramUserId);
}
