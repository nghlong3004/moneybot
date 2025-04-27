package com.nghlong3004.moneybot.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nghlong3004.moneybot.model.User;
import com.nghlong3004.moneybot.repository.IUserRepository;
import com.nghlong3004.moneybot.repository.jdbc.UserRepository;
import com.nghlong3004.moneybot.service.IUserService;

public class UserService implements IUserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
  private static UserService INSTANCE;
  private final IUserRepository userRepository;

  public static synchronized UserService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UserService();
    }
    return INSTANCE;
  }

  private UserService() {
    LOGGER.info("Initialized UserService");
    userRepository = new UserRepository();
  }


  @Override
  public boolean insertUser(User user) {
    LOGGER.info("UserService: {}", "Insert user");
    return userRepository.insertUser(user);
  }

  @Override
  public boolean updateUser(User user) {
    LOGGER.info("UserService: {}", "Update user");
    return userRepository.updateUser(user);
  }

  @Override
  public List<User> searchUsersByKeyword(String keyword) {
    LOGGER.info("UserService: {}", "Search user by keyword");
    return userRepository.searchUsersByKeyword(keyword);
  }

  @Override
  public boolean existsByTelegramUserId(Long telegramUserId) {
    LOGGER.info("UserService: {}", "Exists by telegram user id");
    return userRepository.existsByTelegramUserId(telegramUserId);
  }

  @Override
  public int countAllUsers() {
    LOGGER.info("UserService: {}", "Count all user");
    return userRepository.countAllUsers();
  }

  @Override
  public boolean deleteUser(Long telegramUserId) {
    LOGGER.info("UserService: {}", "Delete User");
    return userRepository.deleteUser(telegramUserId);
  }

}
