package com.nghlong3004.moneybot.service;

import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface TransactionService {
  boolean isTransaction(String text);

  String handleTransaction(Message message);
}
