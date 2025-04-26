package com.nghlong3004.moneybot.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class User {
  private Long id;
  private Long telegramUserId;
  private String username;
  private String firstName;
  private String lastName;
  private String languageCode;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Boolean isActive;
  private BigDecimal monthlyBudget;
  private String currency;
  private String note;


  private User(Builder builder) {
    this.id = builder.id;
    this.telegramUserId = builder.telegramUserId;
    this.username = builder.username;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.languageCode = builder.languageCode;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.isActive = builder.isActive;
    this.monthlyBudget = builder.monthlyBudget;
    this.currency = builder.currency;
    this.note = builder.note;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private Long telegramUserId;
    private String username;
    private String firstName;
    private String lastName;
    private String languageCode;
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
    private Boolean isActive = true;
    private BigDecimal monthlyBudget = BigDecimal.ZERO;
    private String currency = "VND";
    private String note = "";

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder telegramUserId(Long telegramUserId) {
      this.telegramUserId = telegramUserId;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder languageCode(String languageCode) {
      this.languageCode = languageCode;
      return this;
    }

    public Builder createdAt(Timestamp createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(Timestamp updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Builder isActive(Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public Builder monthlyBudget(BigDecimal monthlyBudget) {
      this.monthlyBudget = monthlyBudget;
      return this;
    }

    public Builder currency(String currency) {
      this.currency = currency;
      return this;
    }

    public Builder note(String note) {
      this.note = note;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public BigDecimal getMonthlyBudget() {
    return monthlyBudget;
  }

  public void setMonthlyBudget(BigDecimal monthlyBudget) {
    this.monthlyBudget = monthlyBudget;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }



}

