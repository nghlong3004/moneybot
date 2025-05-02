package com.nghlong3004.moneybot.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nghlong3004.moneybot.constant.SpreadsheetLinkStatus;
import com.nghlong3004.moneybot.model.User;
import com.nghlong3004.moneybot.repository.UserRepository;
import com.nghlong3004.moneybot.repository.jdbc.UserRepositoryImpl;
import com.nghlong3004.moneybot.service.UserService;

public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
  private static UserServiceImpl INSTANCE;
  private final UserRepository userRepository;

  public static synchronized UserServiceImpl getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UserServiceImpl();
    }
    return INSTANCE;
  }

  private UserServiceImpl() {
    LOGGER.info("Initialized UserService");
    userRepository = new UserRepositoryImpl();
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

  @Override
  public boolean hasSpreadsheetId(Long telegramUserId) {
    LOGGER.info("UserService: {}", "Has Spreadsheet Id");
    return userRepository.hasSpreadsheetId(telegramUserId);
  }

  @Override
  public String getSpreadsheetId(Long telegramUserId) {
    LOGGER.info("UserService: {}", "Get Spreadsheet Id");
    return userRepository.getSpreadsheetId(telegramUserId);
  }

  @Override
  public boolean updateSpreadsheetStatus(Long telegramUserId, SpreadsheetLinkStatus status) {
    LOGGER.info("UserService: {}", "Update Spreadsheet status");
    return userRepository.updateSpreadsheetStatus(telegramUserId, status);
  }

  @Override
  public User findUserByTelegramId(Long telegramUserId) {
    LOGGER.info("UserService: {}", "Find user by telegram id");
    return userRepository.findUserByTelegramId(telegramUserId);
  }

  @Override
  public boolean updateSpreadsheetId(Long telegramUserId, String spreadsheetId) {
    LOGGER.info("UserService: {}", "Update Spreadsheet id");
    return userRepository.updateSpreadsheetId(telegramUserId, spreadsheetId);
  }

}
