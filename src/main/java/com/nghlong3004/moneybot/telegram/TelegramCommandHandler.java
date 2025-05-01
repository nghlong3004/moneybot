package com.nghlong3004.moneybot.telegram;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import com.nghlong3004.moneybot.service.StatisticService;
import com.nghlong3004.moneybot.util.BotCommandUtil;

public class TelegramCommandHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramCommandHandler.class);
  private final Map<String, Consumer<Long>> commands;
  private final ITelegramGateway telegramGateway;

  public TelegramCommandHandler(StatisticService statisticService,
      ITelegramGateway telegramGateway) {
    this.telegramGateway = telegramGateway;
    commands = new HashMap<>();
    commands.put("/start", id -> telegramGateway.sendMessage(id, BotCommandUtil.getStartMessage()));
    commands.put("/today",
        id -> telegramGateway.sendMessage(id, statisticService.getTodayStats(id)));
    commands.put("/last_3_days",
        id -> telegramGateway.sendMessage(id, statisticService.getLastNDaysStats(id, 3)));
    commands.put("/last_7_days",
        id -> telegramGateway.sendMessage(id, statisticService.getLastNDaysStats(id, 7)));
    commands.put("/this_week",
        id -> telegramGateway.sendMessage(id, statisticService.getThisWeekStats(id)));
    commands.put("/this_month",
        id -> telegramGateway.sendMessage(id, statisticService.getThisMonthStats(id)));
    commands.put("/last_month",
        id -> telegramGateway.sendMessage(id, statisticService.getLastMonthStats(id)));
    commands.put("/this_quarter",
        id -> telegramGateway.sendMessage(id, statisticService.getThisQuarterStats(id)));
    commands.put("/this_year",
        id -> telegramGateway.sendMessage(id, statisticService.getThisYearStats(id)));
    commands.put("/help", id -> telegramGateway.sendMessage(id, BotCommandUtil.getHelpMessage()));
    commands.put("/help_not_link", id -> telegramGateway.sendMessage(id, BotCommandUtil.getHelpNotLinkMessage()));
    commands.put("/sheet_guide",
        id -> telegramGateway.sendMessage(id, BotCommandUtil.getSheetGuideMessage()));
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
