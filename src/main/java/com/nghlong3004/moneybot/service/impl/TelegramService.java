package com.nghlong3004.moneybot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import com.nghlong3004.moneybot.service.IStatisticService;
import com.nghlong3004.moneybot.service.ITelegramService;
import com.nghlong3004.moneybot.service.ITransactionService;
import com.nghlong3004.moneybot.telegram.ITelegramGateway;
import com.nghlong3004.moneybot.telegram.TelegramCommandHandler;
import com.nghlong3004.moneybot.telegram.TelegramGateway;
import com.nghlong3004.moneybot.telegram.TelegramTransactionProcessor;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;

public class TelegramService implements ITelegramService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramService.class);

  private static final TelegramService INSTANCE = new TelegramService();

  private TelegramBotsLongPollingApplication botsApplication;

  private TelegramService() {
    LOGGER.info("Initialized TelegramBotManager");
  }

  public static TelegramService getInstance() {
    return INSTANCE;
  }

  @Override
  public void startBot() {
    try {
      String token = ObjectContainerUtil.getPropertyUtil().getTelegramToken();

      ITelegramGateway telegramGateway = new TelegramGateway(token);
      IStatisticService statisticService = new StatisticService();
      ITransactionService transactionService = new TransactionService();

      TelegramBotService botService =
          new TelegramBotService(new TelegramCommandHandler(statisticService, telegramGateway),
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
