package com.nghlong3004.moneybot.telegram;

import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import com.nghlong3004.moneybot.service.IStatisticService;
import com.nghlong3004.moneybot.util.BotCommandUtil;

public class TelegramCommandHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramCommandHandler.class);
  private final Map<String, Consumer<Long>> commands;
  private final ITelegramGateway telegramGateway;

  public TelegramCommandHandler(IStatisticService statisticService,
      ITelegramGateway telegramGateway) {
    this.telegramGateway = telegramGateway;
    commands = Map.of("/start",
        id -> telegramGateway.sendMessage(id, BotCommandUtil.getHelpMessage()), "/today",
        id -> telegramGateway.sendMessage(id, statisticService.getTodayStats(id)), "/last_3_days",
        id -> telegramGateway.sendMessage(id, statisticService.getLastNDaysStats(id, 3)),
        "/last_7_days",
        id -> telegramGateway.sendMessage(id, statisticService.getLastNDaysStats(id, 7)),
        "/this_week", id -> telegramGateway.sendMessage(id, statisticService.getThisWeekStats(id)),
        "/this_month",
        id -> telegramGateway.sendMessage(id, statisticService.getThisMonthStats(id)),
        "/last_month",
        id -> telegramGateway.sendMessage(id, statisticService.getLastMonthStats(id)),
        "/this_quarter",
        id -> telegramGateway.sendMessage(id, statisticService.getThisQuarterStats(id)),
        "/this_year", id -> telegramGateway.sendMessage(id, statisticService.getThisYearStats(id)),
        "/help", id -> telegramGateway.sendMessage(id, BotCommandUtil.getHelpMessage()));
    LOGGER.info("CommandHandler initialized with commands: {}", commands.keySet());
  }

  public void handleCommand(Message message) {
    commands
        .getOrDefault(message.getText(),
            id -> telegramGateway.sendMessage(id, BotCommandUtil.getHelpMessage()))
        .accept(message.getChatId());
    LOGGER.debug("Handled command '{}' from chatId={}", message.getText(), message.getChatId());
  }
}
