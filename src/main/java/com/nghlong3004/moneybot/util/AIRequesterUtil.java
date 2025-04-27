package com.nghlong3004.moneybot.util;

import java.net.URI;
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
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.nghlong3004.moneybot.constant.APIConstant;

public class AIRequesterUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(AIRequesterUtil.class);
  private ChatCompletionsClient chatCompletionsClient;
  private Client client;
  private final String token;

  protected AIRequesterUtil(String token) {
    this.token = token;
  }

  public String askOpenAI(String message, String model) {
    buildChatCompletionsClient();
    try {
      LOGGER.info("Sending message to AI: {}", message);
      List<ChatRequestMessage> chatMessages =
          Collections.singletonList(new ChatRequestUserMessage(message));

      ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
      options.setModel(model);

      ChatCompletions completions = chatCompletionsClient.complete(options);
      String reply = completions.getChoice().getMessage().getContent();

      LOGGER.info("Received reply from AI: {}", reply);
      return reply;
    } catch (Exception e) {
      LOGGER.error("Error when calling AI API", e);
      return "Error when calling AI API.";
    }
  }

  public String askTextforGemini(String message) {
    buildGeminiClient();
    LOGGER.info("Sending message to AI: {}", message);

    GenerateContentResponse responseFuture =
        client.models.generateContent(APIConstant.MODEL_GEMINI, message, null);

    LOGGER.info("Received reply from AI: {}", responseFuture.text());
    return responseFuture.text();
  }

  public String askImageforGemini(String text, URI path) {
    buildChatCompletionsClient();
    LOGGER.info("Sending Image to AI: {}", path);

    Content content =
        Content.fromParts(Part.fromText(text), Part.fromUri(path.toString(), "image/jpeg"));

    GenerateContentResponse responseFuture =
        client.models.generateContent(APIConstant.MODEL_GEMINI, content, null);

    LOGGER.info("Received reply from AI: {}", responseFuture.text());
    return responseFuture.text();
  }

  private void buildChatCompletionsClient() {
    if (this.chatCompletionsClient == null) {
      synchronized (this) {
        if (this.chatCompletionsClient == null) {
          this.chatCompletionsClient =
              new ChatCompletionsClientBuilder().credential(new AzureKeyCredential(this.token))
                  .endpoint(APIConstant.ENDPOINT_AI).buildClient();
        }
      }
    }
  }

  private void buildGeminiClient() {
    if (this.client == null) {
      synchronized (this) {
        if (this.client == null) {
          this.client = Client.builder()
              .apiKey(ObjectContainerUtil.getPropertyUtil().getGeminiApiKey()).build();
        }
      }
    }
  }

}
