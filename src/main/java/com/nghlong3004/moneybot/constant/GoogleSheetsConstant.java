package com.nghlong3004.moneybot.constant;

public enum GoogleSheetsConstant {
  EAT_OUT("ăn ngoài", 71), FOOD("thức ăn", 74), MANDATORY_SPENDING("chi tiêu bắt buộc",
      77), OTHER_EXPENSES("chi tiêu khác",
          80), TRANSPORTATION_TYPE("phương tiện đi lại", 83), INVEST("đầu tư", 86);

  private final String explainVI;
  private final int index;

  private GoogleSheetsConstant(String explainVI, int index) {
    this.explainVI = explainVI;
    this.index = index;
  }

  public String getExplainVI() {
    return this.explainVI;
  }

  public int getIndex() {
    return this.index;
  }

}
