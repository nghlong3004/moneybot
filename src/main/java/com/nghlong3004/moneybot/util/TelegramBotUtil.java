package com.nghlong3004.moneybot.util;

import java.sql.Timestamp;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.nghlong3004.moneybot.configuration.MessageConfiguration;
import com.nghlong3004.moneybot.constant.APIConstant;
import com.nghlong3004.moneybot.constant.PromptConstant;
import com.nghlong3004.moneybot.constant.TelegramConstant;
import com.nghlong3004.moneybot.model.Expense;
import com.nghlong3004.moneybot.model.ai.AI;

public class TelegramBotUtil implements LongPollingSingleThreadUpdateConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUtil.class);
  private final TelegramClient telegramClient;

  protected TelegramBotUtil(String token) {
    LOGGER.info("Initialized TelegramBotUtil");
    telegramClient = new OkHttpTelegramClient(token);
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
    if (isCommand(messageModel.getMessageText())) {
      handleExpenseStatisticRequest(messageModel);
    } else {
      processTransactionEntry(messageModel);
    }

  }

  private void processTransactionEntry(MessageConfiguration messageModel) {
    String checkPrompt =
        String.format(PromptConstant.CHECK_IS_TRANSACTION_PROMPT, messageModel.getMessageText());
    String checkResult =
        ObjectContainerUtil.getAiRequesterUtil().ask(checkPrompt, APIConstant.MODE_AI_NANO);
    if (checkResult.startsWith("NO")) {
      sendHelpMessage(messageModel.getChatID());
      return;
    }
    String now = LocalDate.now().toString();
    String prompt = String.format(PromptConstant.CLASSIFY_TRANSACTION_PROMPT, now,
        messageModel.getMessageText());
    String aiResponse =
        ObjectContainerUtil.getAiRequesterUtil().ask(prompt, APIConstant.MODEL_AI_LLM_4M);
    List<AI> results = parseJsonToAIModel(aiResponse);
    for (AI result : results) {
      Expense expense =
          Expense.builder().userId(messageModel.getChatID()).amount(result.getAmount())
              .type(result.getType()).description(result.getPeriodOfDay() + result.getCategory())
              .updatedAt(new Timestamp(System.currentTimeMillis())).build();
    }
    String confirmPrompt =
        String.format(PromptConstant.CONFIRM_TRANSACTION_REPLY_PROMPT, aiResponse);
    sendTextMessage(messageModel.getChatID(),
        ObjectContainerUtil.getAiRequesterUtil().ask(confirmPrompt, APIConstant.MODEL_AI_DSK));
  }

  private void handleExpenseStatisticRequest(MessageConfiguration messageModel) {
    TelegramConstant command = findByMessageText(messageModel.getMessageText());
    if (command == null) {
      sendHelpMessage(messageModel.getChatID());
      return;
    }
    String replyMessage = "";
    switch (command) {
      case TODAY:
        break;
      case LAST_3_DAYS:
        break;
      case LAST_7_DAYS:
        break;
      case THIS_MONTH:
        break;
      case LAST_MONTH:
        break;
      case THIS_QUARTER:
        break;
      case THIS_WEEK:
        break;
      case THIS_YEAR:
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
      String trimmedJson = jsonString.trim();
      if (trimmedJson.startsWith("[")) {
        return objectMapper.readValue(trimmedJson, new TypeReference<List<AI>>() {});
      } else if (trimmedJson.startsWith("{")) {
        ais.add(objectMapper.readValue(trimmedJson, AI.class));
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

}
