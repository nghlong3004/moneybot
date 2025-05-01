package com.nghlong3004.moneybot.constant;

public enum TelegramConstant {
  TODAY("/today"), LAST_3_DAYS("/last_3_days"), LAST_7_DAYS("/last_7_days"), THIS_WEEK(
      "/this_week"), THIS_MONTH("/this_month"), LAST_MONTH(
          "/last_month"), THIS_QUARTER("/this_quarter"), THIS_YEAR("/this_year"), START("/start"), HELP("/help"),
  SHEET_GUIDE("/sheet_guide"), HELP_NOT_LINK("/help_not_link");

  private final String CODE;

  TelegramConstant(String code) {
    this.CODE = code;
  }

  public String getCode() {
    return CODE;
  }


}
