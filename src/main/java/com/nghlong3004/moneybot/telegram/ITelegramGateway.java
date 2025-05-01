package com.nghlong3004.moneybot.telegram;

public interface ITelegramGateway {
  void sendMessage(Long chatID, String message);
}
