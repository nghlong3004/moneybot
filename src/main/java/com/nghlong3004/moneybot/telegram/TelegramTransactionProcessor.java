package com.nghlong3004.moneybot.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import com.nghlong3004.moneybot.service.TransactionService;

public class TelegramTransactionProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramTransactionProcessor.class);
  private final TransactionService transactionService;
  private final ITelegramGateway telegramGateway;

  public TelegramTransactionProcessor(TransactionService transactionService,
      ITelegramGateway telegramGateway) {
    this.transactionService = transactionService;
    this.telegramGateway = telegramGateway;
    LOGGER.info("TransactionProcessor initialized.");
  }

  public void processTransaction(Message message) {
    if (!transactionService.isTransaction(message.getText())) {
      telegramGateway.sendMessage(message.getChatId(),
          "Đây không phải 1 tin nhắn liên quan tới chi tiêu, hãy nhắn lại!!");
      LOGGER.debug("Invalid transaction from chatId={}", message.getChatId());
      return;
    }
    telegramGateway.sendMessage(message.getChatId(), transactionService.handleTransaction(message));
    LOGGER.debug("Transaction processed for chatId={}", message.getChatId());
  }
  public void processNonTransaction(Message message) {
    telegramGateway.sendMessage(message.getChatId(), message.getText());
    LOGGER.debug("Transaction processed for chatId={}", message.getChatId());
  }
}
