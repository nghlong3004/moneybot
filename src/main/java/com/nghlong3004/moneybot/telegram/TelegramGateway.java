package com.nghlong3004.moneybot.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TelegramGateway implements ITelegramGateway {

  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramGateway.class);
  private final TelegramClient telegramClient;

  public TelegramGateway(String botToken) {
    this.telegramClient = new OkHttpTelegramClient(botToken);
    LOGGER.info("Initialized TelegramGatewayImpl");
  }

  @Override
  public void sendMessage(Long chatId, String messageText) {
    try {
      telegramClient.execute(SendMessage.builder().chatId(chatId).text(messageText).build());
      LOGGER.debug("Sent message to chatId={}: {}", chatId, messageText);
    } catch (TelegramApiException e) {
      LOGGER.error("Failed to send message to chatId={}", chatId, e);
    }
  }

}
