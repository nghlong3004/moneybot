package com.nghlong3004.moneybot.util;

import com.nghlong3004.moneybot.configuration.DatabaseConfiguration;

public class ObjectContainerUtil {

  private static final PropertyUtil PROPERTY_UTIL = new PropertyUtil();
  
  private static final GoogleSheetUtil GOOGLE_SHEET_UTIL = new GoogleSheetUtil();

  private static final DatabaseConfiguration DATABASE_CONFIGURATION =
      new DatabaseConfiguration(getPropertyUtil().getDbUrl(), getPropertyUtil().getDbUsername(),
          getPropertyUtil().getDbPassword(), getPropertyUtil().getDbClassName());

  private static final DatabaseUtil DATABASE_UTIL = new DatabaseUtil(getDatabaseConfiguration());

  private static final TelegramBotUtil TELEGRAM_BOT_UTIL =
      new TelegramBotUtil(getPropertyUtil().getTelegramToken());

  private static final AIRequesterUtil AI_REQUESTER_UTIL =
      new AIRequesterUtil(getPropertyUtil().getOpenAIApiKey());

  private ObjectContainerUtil() {}

  public static PropertyUtil getPropertyUtil() {
    return PROPERTY_UTIL;
  }

  public static TelegramBotUtil getTelegramBotUtil() {
    return TELEGRAM_BOT_UTIL;
  }

  public static DatabaseUtil getDatabaseUtil() {
    return DATABASE_UTIL;
  }

  private static DatabaseConfiguration getDatabaseConfiguration() {
    return DATABASE_CONFIGURATION;
  }

  public static AIRequesterUtil getAiRequesterUtil() {
    return AI_REQUESTER_UTIL;
  }

  public static GoogleSheetUtil getGoogleSheetUtil() {
    return GOOGLE_SHEET_UTIL;
  }


}
