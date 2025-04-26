package com.nghlong3004.moneybot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nghlong3004.moneybot.configuration.DatabaseConfiguration;

public class DatabaseUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);
  private final DatabaseConfiguration configuration;

  protected DatabaseUtil(DatabaseConfiguration configuration) {
    LOGGER.info("Initialized DatabaseUtil");
    this.configuration = configuration;
    try {
      Class.forName(configuration.getDbClassName());
    } catch (ClassNotFoundException e) {
      LOGGER.debug("Class not found {}", e.getMessage());
      e.printStackTrace();
    }
  }

  public Connection getConnection() throws SQLException {
    LOGGER.info("Connection Database {}", configuration.getDbUsername());
    return DriverManager.getConnection(configuration.getDbUrl(), configuration.getDbUsername(),
        configuration.getDbPassword());
  }


}
