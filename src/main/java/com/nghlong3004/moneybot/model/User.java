package com.nghlong3004.moneybot.model;

import com.nghlong3004.moneybot.constant.SpreadsheetLinkStatus;

public class User {

  private Long telegramUserId;
  private String username;
  private String firstName;
  private String lastName;
  private String spreadsheetId;
  private SpreadsheetLinkStatus spreadsheetStatus;

  public User() {}

  public User(Long telegramUserId, String username, String firstName, String lastName,
      String spreadsheetId, SpreadsheetLinkStatus spreadsheetStatus) {
    super();
    this.telegramUserId = telegramUserId;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.spreadsheetId = spreadsheetId;
    this.spreadsheetStatus = spreadsheetStatus;
  }

  public String getSpreadsheetId() {
    return spreadsheetId;
  }

  public void setSpreadsheetId(String spreadsheetId) {
    this.spreadsheetId = spreadsheetId;
  }

  public SpreadsheetLinkStatus getSpreadsheetStatus() {
    return spreadsheetStatus;
  }

  public void setSpreadsheetStatus(SpreadsheetLinkStatus spreadsheetStatus) {
    this.spreadsheetStatus = spreadsheetStatus;
  }

  public Long getTelegramUserId() {
    return telegramUserId;
  }

  public void setTelegramUserId(Long telegramUserId) {
    this.telegramUserId = telegramUserId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


}

