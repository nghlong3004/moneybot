package com.nghlong3004.money;

import com.nghlong3004.moneybot.service.TelegramService;
import com.nghlong3004.moneybot.service.impl.TelegramServiceImpl;

public class MainTest {
  
  public static void main(String[] args) {
    TelegramService telegramService = TelegramServiceImpl.getInstance();
    
    telegramService.startBot();
  }
  
}
