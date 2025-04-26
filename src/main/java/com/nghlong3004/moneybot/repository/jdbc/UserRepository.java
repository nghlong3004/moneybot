package com.nghlong3004.moneybot.repository.jdbc;

import java.util.List;
import java.util.Optional;
import com.nghlong3004.moneybot.model.User;
import com.nghlong3004.moneybot.repository.IUserRepository;

public class UserRepository implements IUserRepository {

  @Override
  public boolean insertUser(User user) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Optional<User> findByTelegramUserId(Long telegramUserId) {
    // TODO Auto-generated method stub
    return Optional.empty();
  }

  @Override
  public boolean updateUser(User user) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean deactivateUser(Long telegramUserId) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<User> findAllActiveUsers() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> searchUsersByKeyword(String keyword) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean existsByTelegramUserId(Long telegramUserId) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int countAllUsers() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int countActiveUsers() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean deleteUser(Long telegramUserId) {
    // TODO Auto-generated method stub
    return false;
  }

}
