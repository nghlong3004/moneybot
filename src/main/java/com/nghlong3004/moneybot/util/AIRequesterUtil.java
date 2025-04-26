package com.nghlong3004.moneybot.util;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletions;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.core.credential.AzureKeyCredential;
import com.nghlong3004.moneybot.constant.APIConstant;

public class AIRequesterUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(AIRequesterUtil.class);
  private final ChatCompletionsClient client;

  protected AIRequesterUtil(String token) {
    this.client = new ChatCompletionsClientBuilder().credential(new AzureKeyCredential(token))
        .endpoint(APIConstant.ENDPOINT_AI).buildClient();
  }

  public String ask(String message, String model) {
    try {
      LOGGER.info("Sending message to AI: {}", message);
      List<ChatRequestMessage> chatMessages =
          Collections.singletonList(new ChatRequestUserMessage(message));

      ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
      options.setModel(model);

      ChatCompletions completions = client.complete(options);
      String reply = completions.getChoice().getMessage().getContent();

      LOGGER.info("Received reply from AI: {}", reply);
      return reply;
    } catch (Exception e) {
      LOGGER.error("Error when calling AI API", e);
      return "Error when calling AI API.";
    }
  }

}
