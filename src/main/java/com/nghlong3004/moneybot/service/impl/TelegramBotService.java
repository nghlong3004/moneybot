package com.nghlong3004.moneybot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import com.nghlong3004.moneybot.telegram.TelegramCommandHandler;
import com.nghlong3004.moneybot.telegram.TelegramTransactionProcessor;

public final class TelegramBotService implements LongPollingSingleThreadUpdateConsumer {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotService.class);
  private final TelegramCommandHandler commandHandler;
  private final TelegramTransactionProcessor transactionProcessor;

  public TelegramBotService(TelegramCommandHandler commandHandler,
      TelegramTransactionProcessor transactionProcessor) {
    this.commandHandler = commandHandler;
    this.transactionProcessor = transactionProcessor;
    LOGGER.info("TelegramBotService initialized");
  }

  @Override
  public void consume(Update update) {
    if (!update.hasMessage() || !update.getMessage().hasText())
      return;
    Message message = update.getMessage();
    LOGGER.debug("Received message from chatId={}: {}", message.getChatId(), message.getText());

    if (message.getText().startsWith("/")) {
      commandHandler.handleCommand(message);
    } else {
      transactionProcessor.processTransaction(message);
    }
  }
}
