package com.nghlong3004.moneybot.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Expense {
  private Long id;
  private Long userId;
  private BigDecimal amount;
  private String type; // "income" or "expense"
  private String description;
  private Timestamp updatedAt;

  private Expense(Builder builder) {
    this.id = builder.id;
    this.userId = builder.userId;
    this.amount = builder.amount;
    this.type = builder.type;
    this.description = builder.description;
    this.updatedAt = builder.updatedAt;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String type;
    private String description;
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder userId(Long userId) {
      this.userId = userId;
      return this;
    }

    public Builder amount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder updatedAt(Timestamp updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Expense build() {
      return new Expense(this);
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }
}
