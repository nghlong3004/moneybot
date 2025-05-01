package com.nghlong3004.moneybot.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.nghlong3004.moneybot.constant.APIConstant;
import com.nghlong3004.moneybot.constant.GoogleSheetsConstant;
import com.nghlong3004.moneybot.constant.PromptConstant;
import com.nghlong3004.moneybot.constant.ai.AIModel;
import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.model.ai.AI;
import com.nghlong3004.moneybot.service.ExpenseService;
import com.nghlong3004.moneybot.service.GoogleSheetsService;
import com.nghlong3004.moneybot.service.TransactionService;
import com.nghlong3004.moneybot.service.UserService;
import com.nghlong3004.moneybot.service.ai.AIFacadeService;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;

public class TransactionServiceImpl implements TransactionService {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
  private final AIFacadeService aiFacadeService;
  private final ExpenseService expenseService;
  private final UserService userService;
  private final ObjectMapper objectMapper;
  private final GoogleSheetsService googleSheetsService;

  public TransactionServiceImpl() {
    this.aiFacadeService =
        AIFacadeService.getInstance(ObjectContainerUtil.getPropertyUtil().getOpenAIApiKey(),
            ObjectContainerUtil.getPropertyUtil().getGeminiApiKey(), APIConstant.ENDPOINT_AI);
    this.expenseService = ExpenseServiceImpl.getInstance();
    this.userService = UserServiceImpl.getInstance();
    this.objectMapper = new ObjectMapper()
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).findAndRegisterModules();
    this.googleSheetsService = GoogleSheetsServiceImpl.getInstance();
    LOGGER.info("TransactionServiceImpl initialized.");
  }

  @Override
  public boolean isTransaction(String text) {
    String checkPrompt = String.format(PromptConstant.CHECK_IS_TRANSACTION_PROMPT, text);
    String response = aiFacadeService.ask(AIModel.GEMINI_FLASH, checkPrompt);
    boolean result = !response.startsWith("NO");
    LOGGER.debug("Transaction check result: {}", result);
    return result;
  }

  @Override
  public String handleTransaction(Message message) {
    String prompt = String.format(PromptConstant.CLASSIFY_TRANSACTION_PROMPT, LocalDateTime.now(),
        message.getText());
    String aiResponse = aiFacadeService.ask(AIModel.GEMINI_FLASH, prompt);

    List<AI> transactions = parseJson(aiResponse);
    transactions.forEach(transaction -> saveExpense(transaction, message.getChatId()));

    String confirmPrompt =
        String.format(PromptConstant.CONFIRM_TRANSACTION_REPLY_PROMPT, aiResponse);
    return aiFacadeService.ask(AIModel.GEMINI_FLASH, confirmPrompt);
  }

  private List<AI> parseJson(String json) {
    try {
      json = json.trim().replaceAll("```|json", "").trim();
      if (json.startsWith("[")) {
        return objectMapper.readValue(json, new TypeReference<List<AI>>() {});
      }
      return List.of(objectMapper.readValue(json, AI.class));
    } catch (Exception e) {
      LOGGER.error("Failed to parse transaction JSON", e);
      throw new RuntimeException("Failed to parse transaction JSON", e);
    }
  }

  private void saveExpense(AI ai, Long userId) {
    Expense expense = Expense.builder().userId(userId).amount(ai.getAmount()).type(ai.getType())
        .description(ai.getPeriodOfDay() + ": " + ai.getCategory()).build();

    if (expense.getAmount().compareTo(BigDecimal.valueOf(1000)) >= 0) {
      expenseService.insertExpense(expense);
      if (expense.getType().equals("income")) {
        writeIncomeToGoogleSheet(ai, userId);
      } else {
        writeExpenseToGoogleSheet(ai, userId);
      }
      LOGGER.debug("Inserted expense: {}", expense);
    } else {
      LOGGER.debug("Ignored expense below threshold: {}", expense);
    }
  }

  private void writeIncomeToGoogleSheet(AI ai, Long telegramUserId) {
    String spreadsheetsId = userService.getSpreadsheetId(telegramUserId);
    int month = ai.getDate().getMonthValue();
    char monthChar = (char) (month + 'A');
    int year = ai.getDate().getYear();
    String data = googleSheetsService.readFromSheet(spreadsheetsId,
        String.format("Tóm tắt %d!%c7:%c7", year, monthChar, monthChar));
    data = data.replaceAll("VND", "").trim().replace(".", "");
    BigDecimal value = ai.getAmount();
    if (data != null && !data.isEmpty()) {
      BigDecimal bonus = new BigDecimal(data);
      value = value.add(bonus);
    }
    List<List<Object>> values = Arrays.asList(Arrays.asList(value));
    googleSheetsService.writeToSheet(spreadsheetsId,
        String.format("Tóm tắt %d!%c7", year, monthChar), values);

  }

  private void writeExpenseToGoogleSheet(AI ai, Long telegramUserId) {
    String spreadsheetsId = userService.getSpreadsheetId(telegramUserId);
    int month = ai.getDate().getMonthValue();
    char columnIndex = getIndex(ai.getSpendingType());

    String range = String.format("Tháng %d!%c:%c", month, columnIndex, columnIndex);
    String[] datas = googleSheetsService.readFromSheet(spreadsheetsId, range).split("\n");

    int rowToWrite = datas.length;
    for (int i = 0; i < datas.length; i++) {
      if (datas[i].isEmpty()) {
        rowToWrite = i + 1;
        break;
      }
    }

    if (rowToWrite == datas.length) {
      googleSheetsService.insertRowAbove(spreadsheetsId, String.format("Tháng %d", month),
          rowToWrite - 1);
    }

    List<List<Object>> values = Arrays
        .asList(Arrays.asList(ai.getCategory(), ai.getDate().getDayOfMonth(), ai.getAmount()));

    String writeRange = String.format("Tháng %d!%c%d", month, columnIndex, rowToWrite);
    googleSheetsService.writeToSheet(spreadsheetsId, writeRange, values);
  }

  private char getIndex(String detail) {
    for (GoogleSheetsConstant sheetsConstant : GoogleSheetsConstant.values()) {
      if (sheetsConstant.getDetail().equalsIgnoreCase(detail)) {
        return sheetsConstant.getIndex();
      }
    }
    return 0;
  }
}
