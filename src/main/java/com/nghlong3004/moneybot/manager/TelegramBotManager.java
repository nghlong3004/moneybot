package com.nghlong3004.moneybot.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;

public class TelegramBotManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotManager.class);

  private static final TelegramBotManager INSTANCE = new TelegramBotManager();

  private TelegramBotsLongPollingApplication botsApplication;

  private TelegramBotManager() {
    LOGGER.info("Initialized TelegramBotManager");
  }

  public static TelegramBotManager getInstance() {
    return INSTANCE;
  }

  public void start() {
    try {
      botsApplication = new TelegramBotsLongPollingApplication();
      botsApplication.registerBot(ObjectContainerUtil.getPropertyUtil().getTelegramToken(),
          ObjectContainerUtil.getTelegramBotUtil());
      LOGGER.info("Telegram bot registered successfully.");
      holdMainThread();
    } catch (Exception e) {
      LOGGER.error("Error while starting bot", e);
      throw new RuntimeException("Failed to start Telegram bot", e);
    }
  }

  private void holdMainThread() {
    try {
      LOGGER.info("Holding main thread for Telegram bot...");
      Thread.currentThread().join();
    } catch (InterruptedException e) {
      LOGGER.error("Main thread interrupted", e);
      Thread.currentThread().interrupt();
    }
  }

}
