package com.nghlong3004.moneybot.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import com.nghlong3004.moneybot.service.ITransactionService;
import com.nghlong3004.moneybot.util.BotCommandUtil;

public class TelegramTransactionProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramTransactionProcessor.class);
  private final ITransactionService transactionService;
  private final ITelegramGateway telegramGateway;

  public TelegramTransactionProcessor(ITransactionService transactionService,
      ITelegramGateway telegramGateway) {
    this.transactionService = transactionService;
    this.telegramGateway = telegramGateway;
    LOGGER.info("TransactionProcessor initialized.");
  }

  public void processTransaction(Message message) {
    if (!transactionService.isTransaction(message.getText())) {
      telegramGateway.sendMessage(message.getChatId(), BotCommandUtil.getHelpMessage());
      LOGGER.debug("Invalid transaction from chatId={}", message.getChatId());
      return;
    }
    telegramGateway.sendMessage(message.getChatId(), transactionService.handleTransaction(message));
    LOGGER.debug("Transaction processed for chatId={}", message.getChatId());
  }
}
