package com.nghlong3004.moneybot.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.nghlong3004.moneybot.constant.APIConstant;
import com.nghlong3004.moneybot.constant.PromptConstant;
import com.nghlong3004.moneybot.constant.ai.AIModel;
import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.model.ai.AI;
import com.nghlong3004.moneybot.service.ITransactionService;
import com.nghlong3004.moneybot.service.ai.AIFacadeService;
import com.nghlong3004.moneybot.service.dao.IExpenseService;
import com.nghlong3004.moneybot.service.dao.impl.ExpenseService;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;

public class TransactionService implements ITransactionService {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
  private final AIFacadeService aiFacadeService;
  private final IExpenseService expenseService;
  private final ObjectMapper objectMapper;

  public TransactionService() {
    this.aiFacadeService =
        AIFacadeService.getInstance(ObjectContainerUtil.getPropertyUtil().getOpenAIApiKey(),
            ObjectContainerUtil.getPropertyUtil().getGeminiApiKey(), APIConstant.ENDPOINT_AI);
    this.expenseService = ExpenseService.getInstance();
    this.objectMapper = new ObjectMapper()
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).findAndRegisterModules();
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
    String prompt = String.format(PromptConstant.CLASSIFY_TRANSACTION_PROMPT, LocalDate.now(),
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
      LOGGER.debug("Inserted expense: {}", expense);
    } else {
      LOGGER.debug("Ignored expense below threshold: {}", expense);
    }
  }
}
