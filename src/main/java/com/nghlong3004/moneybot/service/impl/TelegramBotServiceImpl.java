package com.nghlong3004.moneybot.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import com.nghlong3004.moneybot.constant.SpreadsheetLinkStatus;
import com.nghlong3004.moneybot.constant.TelegramConstant;
import com.nghlong3004.moneybot.model.User;
import com.nghlong3004.moneybot.service.UserService;
import com.nghlong3004.moneybot.telegram.TelegramCommandHandler;
import com.nghlong3004.moneybot.telegram.TelegramTransactionProcessor;
import com.nghlong3004.moneybot.util.BotCommandUtil;

public final class TelegramBotServiceImpl implements LongPollingSingleThreadUpdateConsumer {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotServiceImpl.class);
  private final TelegramCommandHandler commandHandler;
  private final TelegramTransactionProcessor transactionProcessor;
  private final UserService userService;

  public TelegramBotServiceImpl(TelegramCommandHandler commandHandler,
      TelegramTransactionProcessor transactionProcessor) {
    this.commandHandler = commandHandler;
    this.transactionProcessor = transactionProcessor;
    this.userService = UserServiceImpl.getInstance();
    LOGGER.info("TelegramBotService initialized");
  }

  @Override
  public void consume(Update update) {
    if (!update.hasMessage() || !update.getMessage().hasText()) {
      LOGGER.debug("No valid message text found in update");
      return;
    }

    Message message = update.getMessage();
    LOGGER.debug("Received message from chatId={}: {}", message.getChatId(), message.getText());

    Chat chat = message.getChat();
    User user =
        Optional.ofNullable(userService.findUserByTelegramId(chat.getId())).orElseGet(() -> {
          LOGGER.debug("User not found, creating new user for chatId={}", chat.getId());
          User newUser = new User(chat.getId(), chat.getUserName(), chat.getFirstName(),
              chat.getLastName(), "", SpreadsheetLinkStatus.NOT_LINKED);
          userService.insertUser(newUser);
          return newUser;
        });

    String messageText = message.getText();

    if (TelegramConstant.START.getCode().equals(messageText)) {
      LOGGER.debug("Processing start command");
      commandHandler.handleCommand(message);
      return;
    }

    if (TelegramConstant.SHEET_GUIDE.getCode().equals(messageText)) {
      LOGGER.debug("Processing sheet guide command");
      handleSheetGuideCommand(user, message);
      return;
    }

    LOGGER.debug("Processing message based on spreadsheet status: {}", user.getSpreadsheetStatus());
    switch (user.getSpreadsheetStatus()) {
      case NOT_LINKED:
        handleNotLinkedStatus(messageText, user, message);
        break;
      case LINKING:
        handleLinkingStatus(messageText, user, message);
        break;
      case LINKED:
        handleLinkedStatus(messageText, message);
        break;
      default:
        LOGGER.warn("Unhandled spreadsheet status encountered: {}", user.getSpreadsheetStatus());
    }
  }

  private void handleSheetGuideCommand(User user, Message message) {
    if (user.getSpreadsheetStatus() == SpreadsheetLinkStatus.LINKED) {
      message.setText("Bạn đã liên kết Google Sheet trước đó rồi!");
      transactionProcessor.processNonTransaction(message);
    } else {
      user.setSpreadsheetStatus(SpreadsheetLinkStatus.LINKING);
      userService.updateSpreadsheetStatus(user.getTelegramUserId(), SpreadsheetLinkStatus.LINKING);
      commandHandler.handleCommand(message);
    }
  }

  private void handleNotLinkedStatus(String messageText, User user, Message message) {
    if (!TelegramConstant.SHEET_GUIDE.getCode().equals(messageText)) {
      message.setText(TelegramConstant.HELP_NOT_LINK.getCode());
    }
    commandHandler.handleCommand(message);
  }

  private void handleLinkingStatus(String messageText, User user, Message message) {
    Optional<String> extractedId = BotCommandUtil.extractSpreadsheetId(messageText);
    if (extractedId.isPresent()) {
      userService.updateSpreadsheetId(user.getTelegramUserId(), extractedId.get());
      userService.updateSpreadsheetStatus(user.getTelegramUserId(), SpreadsheetLinkStatus.LINKED);
      message.setText("Liên kết Google Sheet thành công!");
      LOGGER.info("Spreadsheet linked successfully for userId={}", user.getTelegramUserId());
    } else {
      message.setText("Link Google Sheet không hợp lệ. Vui lòng gửi lại link chính xác.");
      LOGGER.warn("Invalid spreadsheet link provided by userId={}", user.getTelegramUserId());
    }
    transactionProcessor.processNonTransaction(message);
  }

  private void handleLinkedStatus(String messageText, Message message) {
    if (messageText.startsWith("/")) {
      commandHandler.handleCommand(message);
    } else {
      transactionProcessor.processTransaction(message);
    }
  }

}
