package com.nghlong3004.moneybot.constant;

public enum GoogleSheetsConstant {
  EAT_OUT("ăn ngoài", (char) 71), FOOD("đi chợ", (char) 74), MANDATORY_SPENDING(
      "chi tiêu bắt buộc",
      (char) 77), OTHER_EXPENSES("chi tiêu khác", (char) 80), TRANSPORTATION_TYPE(
          "phương tiện đi lại", (char) 83), INVEST("đầu tư", (char) 86);

  private final String detail;
  private final char index;

  private GoogleSheetsConstant(String detail, char index) {
    this.detail = detail;
    this.index = index;
  }

  public String getDetail() {
    return this.detail;
  }

  public char getIndex() {
    return this.index;
  }

}
