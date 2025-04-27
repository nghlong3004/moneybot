package com.nghlong3004.moneybot.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.nghlong3004.moneybot.configuration.MessageConfiguration;
import com.nghlong3004.moneybot.constant.PromptConstant;
import com.nghlong3004.moneybot.constant.TelegramConstant;
import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.model.User;
import com.nghlong3004.moneybot.model.ai.AI;
import com.nghlong3004.moneybot.service.IExpenseService;
import com.nghlong3004.moneybot.service.IUserService;
import com.nghlong3004.moneybot.service.impl.ExpenseService;
import com.nghlong3004.moneybot.service.impl.UserService;

public class TelegramBotUtil implements LongPollingSingleThreadUpdateConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUtil.class);
  private final TelegramClient telegramClient;
  private final IExpenseService expenseService;
  private final IUserService userService;

  protected TelegramBotUtil(String token) {
    LOGGER.info("Initialized TelegramBotUtil");
    telegramClient = new OkHttpTelegramClient(token);
    expenseService = ExpenseService.getInstance();
    userService = UserService.getInstance();
  }

  @Override
  public void consume(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      LOGGER.info("Message has Text");
      processIncomingMessageHasText(update.getMessage());
    }
  }

  private void processIncomingMessageHasText(Message message) {
    MessageConfiguration messageModel =
        new MessageConfiguration(message.getChat().getId(), message.getChatId(),
            message.getChat().getFirstName() + message.getChat().getLastName(), message.getText());
    if (!userService.existsByTelegramUserId(messageModel.getSenderId())) {
      insertUserToDatabase(new User(messageModel.getSenderId(), message.getChat().getUserName(),
          message.getChat().getFirstName(), message.getChat().getLastName()));
    }
    if (isCommand(messageModel.getMessageText())) {
      handleExpenseStatisticRequest(messageModel);
    } else {
      processTransactionEntry(messageModel);
    }

  }

  private void insertUserToDatabase(User user) {
    if (userService.insertUser(user)) {
      LOGGER.info("Insert user is success");
    } else {
      LOGGER.error("Insert user error {}", "ERROR");
    }

  }

  private void processTransactionEntry(MessageConfiguration messageModel) {
    String checkPrompt =
        String.format(PromptConstant.CHECK_IS_TRANSACTION_PROMPT, messageModel.getMessageText());
    String checkResult = ObjectContainerUtil.getAiRequesterUtil().askGemini(checkPrompt);
    if (checkResult.startsWith("NO")) {
      sendHelpMessage(messageModel.getChatID());
      return;
    }
    String now = LocalDate.now().toString();
    String prompt = String.format(PromptConstant.CLASSIFY_TRANSACTION_PROMPT, now,
        messageModel.getMessageText());
    String aiResponse = ObjectContainerUtil.getAiRequesterUtil().askGemini(prompt);
    List<AI> results = parseJsonToAIModel(aiResponse);
    for (AI result : results) {
      Expense expense = Expense.builder().userId(messageModel.getChatID())
          .amount(result.getAmount()).type(result.getType())
          .description(result.getPeriodOfDay() + ": " + result.getCategory()).build();
      if (expense.getAmount().compareTo(BigDecimal.valueOf(1000)) >= 0) {
        expenseService.insertExpense(expense);
      }
    }
    String confirmPrompt =
        String.format(PromptConstant.CONFIRM_TRANSACTION_REPLY_PROMPT, aiResponse);
    sendTextMessage(messageModel.getChatID(),
        ObjectContainerUtil.getAiRequesterUtil().askGemini(confirmPrompt));
  }

  private void handleExpenseStatisticRequest(MessageConfiguration messageModel) {
    TelegramConstant command = findByMessageText(messageModel.getMessageText());
    if (command == null) {
      sendHelpMessage(messageModel.getChatID());
      return;
    }
    String replyMessage = "";
    long income = 0;
    long expense = 0;
    switch (command) {
      case TODAY:
        income = expenseService.calculateTotalSpendingForToday(messageModel.getChatID(), "income");
        expense =
            expenseService.calculateTotalSpendingForToday(messageModel.getChatID(), "expense");
        replyMessage = String.format("Hôm nay bạn đã thu %s VND, và chi %s VND", income, expense);
        break;
      case LAST_3_DAYS:
        income =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "income", 3);
        expense =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "expense", 3);
        replyMessage = String.format("3 hôm nay bạn đã thu %s VND, và chi %s VND", income, expense);
        break;
      case LAST_7_DAYS:
        income =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "income", 7);
        expense =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "expense", 7);
        replyMessage = String.format("7 hôm nay bạn đã thu %s VND, và chi %s VND", income, expense);
        break;
      case THIS_MONTH:
        income =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "income", 30);
        expense =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "expense", 30);
        replyMessage = String.format("tháng này bạn đã thu %s VND, và chi %s VND", income, expense);
        break;
      case LAST_MONTH:
        income =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "income", 30);
        expense =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "expense", 30);
        replyMessage =
            String.format("tháng trước bạn đã thu %s VND, và chi %s VND", income, expense);
        break;
      case THIS_QUARTER:
        income =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "income", 30);
        expense =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "expense", 30);
        replyMessage = String.format("quý này bạn đã thu %s VND, và chi %s VND", income, expense);
        break;
      case THIS_WEEK:
        income =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "income", 7);
        expense =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "expense", 7);
        replyMessage = String.format("tuần này bạn đã thu %s VND, và chi %s VND", income, expense);
        break;
      case THIS_YEAR:
        income =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "income", 365);
        expense =
            expenseService.calculateTotalSpendingForNDays(messageModel.getChatID(), "expense", 365);
        replyMessage = String.format("năm nay bạn đã thu %s VND, và chi %s VND", income, expense);
        break;
      default:
        replyMessage = BotCommandUtil.getHelpMessage();
        break;
    }
    sendTextMessage(messageModel.getChatID(), replyMessage);
  }

  private void sendHelpMessage(Long chatId) {
    sendTextMessage(chatId, BotCommandUtil.getHelpMessage());
  }

  private void sendTextMessage(Long chatId, String text) {
    try {
      telegramClient.execute(SendMessage.builder().chatId(chatId).text(text).build());
      LOGGER.info("Sent message to user success!");
    } catch (TelegramApiException e) {
      LOGGER.error("Failed to send message: {}", e.getMessage(), e);
    }
  }

  private boolean isCommand(String text) {
    if (text == null) {
      return false;
    }
    return text.startsWith("/");
  }

  private TelegramConstant findByMessageText(String messageText) {
    TelegramConstant telegramConstant = null;
    for (TelegramConstant period : TelegramConstant.values()) {
      if (period.getCode().equalsIgnoreCase(messageText)) {
        telegramConstant = period;
        break;
      }
    }
    return telegramConstant;
  }

  private List<AI> parseJsonToAIModel(String jsonString) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    objectMapper.findAndRegisterModules();
    List<AI> ais = new ArrayList<AI>();
    try {
      jsonString = fixJsonString(jsonString);
      if (jsonString.startsWith("[")) {
        return objectMapper.readValue(jsonString, new TypeReference<List<AI>>() {});
      } else if (jsonString.startsWith("{")) {
        ais.add(objectMapper.readValue(jsonString, AI.class));
        return ais;
      } else {
        LOGGER.error("Invalid JSON format: must start with { or [");
        throw new RuntimeException("Invalid JSON format");
      }
    } catch (JsonProcessingException e) {
      LOGGER.error("Failed to parse AI JSON: {}", e.getMessage(), e);
      throw new RuntimeException("Unable to parse AI JSON", e);
    }
  }

  private String fixJsonString(String jsonString) {
    if (!jsonString.trim().startsWith("[") && !jsonString.trim().startsWith("{")) {
      jsonString = jsonString.replaceAll("```", "");
      jsonString = jsonString.replace("json", "");
    }
    return jsonString.trim();
  }

}
