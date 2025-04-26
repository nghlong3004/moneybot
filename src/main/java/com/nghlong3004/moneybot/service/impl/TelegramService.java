package com.nghlong3004.moneybot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nghlong3004.moneybot.manager.TelegramBotManager;
import com.nghlong3004.moneybot.service.ITelegramService;

public class TelegramService implements ITelegramService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramService.class);

  private static final TelegramService INSTANCE = new TelegramService();

  private TelegramService() {
    LOGGER.info("Initialized TelegramService");
  }

  public static TelegramService getInstance() {
    return INSTANCE;
  }

  @Override
  public void startBot() {
    TelegramBotManager.getInstance().start();
  }

}
