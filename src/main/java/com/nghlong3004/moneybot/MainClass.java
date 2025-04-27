package com.nghlong3004.moneybot;

import com.nghlong3004.moneybot.service.ITelegramService;
import com.nghlong3004.moneybot.service.impl.TelegramService;

public class MainClass {
  
  public static void main(String[] args) {
    ITelegramService telegramService = TelegramService.getInstance();
    telegramService.startBot();
  }
  
}
