package com.nghlong3004.moneybot.configuration;

public class DatabaseConfiguration {
  private final String dbUrl;
  private final String dbUsername;
  private final String dbPassword;
  private final String dbClassname;

  public DatabaseConfiguration(String dbUrl, String dbUsername, String dbPassword,
      String className) {
    this.dbUrl = dbUrl;
    this.dbUsername = dbUsername;
    this.dbPassword = dbPassword;
    this.dbClassname = className;
  }

  public String getDbUrl() {
    return dbUrl;
  }

  public String getDbUsername() {
    return dbUsername;
  }

  public String getDbPassword() {
    return dbPassword;
  }

  public String getDbClassName() {
    return dbClassname;
  }
}
