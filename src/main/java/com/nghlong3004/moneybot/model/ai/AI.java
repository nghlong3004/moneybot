package com.nghlong3004.moneybot.model.ai;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AI {
  private String type;
  private BigDecimal amount;
  private String category;
  private String periodOfDay;
  private LocalDate date;
  private String spendingType;

  public AI() {}

  public AI(String type, BigDecimal amount, String category, String periodOfDay, LocalDate date,
      String spendingType) {
    super();
    this.type = type;
    this.amount = amount;
    this.category = category;
    this.periodOfDay = periodOfDay;
    this.date = date;
    this.spendingType = spendingType;
  }



  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getPeriodOfDay() {
    return periodOfDay;
  }

  public void setPeriodOfDay(String periodOfDay) {
    this.periodOfDay = periodOfDay;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getSpendingType() {
    return spendingType;
  }

  public void setSpendingType(String spendingType) {
    this.spendingType = spendingType;
  }

}
