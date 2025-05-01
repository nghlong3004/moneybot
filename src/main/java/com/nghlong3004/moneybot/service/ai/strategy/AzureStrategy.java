package com.nghlong3004.moneybot.service.ai.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletions;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.core.credential.AzureKeyCredential;
import com.nghlong3004.moneybot.constant.ai.AIModel;
import com.nghlong3004.moneybot.exception.AIException;

public class AzureStrategy implements AIClientStrategy {
  private static final Logger LOGGER = LoggerFactory.getLogger(AzureStrategy.class);
  private final ChatCompletionsClient client;

  public AzureStrategy(String token, String endpoint) {
    this.client = new ChatCompletionsClientBuilder().credential(new AzureKeyCredential(token))
        .endpoint(endpoint).buildClient();
  }

  @Override
  public String chat(String prompt, AIModel model) throws AIException {
    try {
      LOGGER.debug("OpenAI chat – model: {}", model.getModelId());
      ChatRequestMessage userMsg = new ChatRequestUserMessage(prompt);
      ChatCompletionsOptions opts = new ChatCompletionsOptions(java.util.List.of(userMsg));
      opts.setModel(model.getModelId());
      ChatCompletions completions = client.complete(opts);
      return completions.getChoice().getMessage().getContent();
    } catch (Exception e) {
      throw new AIException("OpenAI failure", e);
    }
  }
}
