package com.nghlong3004.moneybot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import com.nghlong3004.moneybot.service.StatisticService;
import com.nghlong3004.moneybot.service.TelegramService;
import com.nghlong3004.moneybot.service.TransactionService;
import com.nghlong3004.moneybot.telegram.ITelegramGateway;
import com.nghlong3004.moneybot.telegram.TelegramCommandHandler;
import com.nghlong3004.moneybot.telegram.TelegramGateway;
import com.nghlong3004.moneybot.telegram.TelegramTransactionProcessor;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;

public class TelegramServiceImpl implements TelegramService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramServiceImpl.class);

  private static final TelegramServiceImpl INSTANCE = new TelegramServiceImpl();

  private TelegramBotsLongPollingApplication botsApplication;

  private TelegramServiceImpl() {
    LOGGER.info("Initialized TelegramBotManager");
  }

  public static TelegramServiceImpl getInstance() {
    return INSTANCE;
  }

  @Override
  public void startBot() {
    try {
      String token = ObjectContainerUtil.getPropertyUtil().getTelegramToken();

      ITelegramGateway telegramGateway = new TelegramGateway(token);
      StatisticService statisticService = new StatisticServiceImpl();
      TransactionService transactionService = new TransactionServiceImpl();

      TelegramBotServiceImpl botService =
          new TelegramBotServiceImpl(new TelegramCommandHandler(statisticService, telegramGateway),
              new TelegramTransactionProcessor(transactionService, telegramGateway));

      botsApplication = new TelegramBotsLongPollingApplication();
      botsApplication.registerBot(token, botService);
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
