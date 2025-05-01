package com.nghlong3004.moneybot.service.ai.strategy;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.nghlong3004.moneybot.constant.ai.AIModel;
import com.nghlong3004.moneybot.exception.AIException;

public class GeminiStrategy implements AIClientStrategy {
  private static final Logger LOGGER = LoggerFactory.getLogger(GeminiStrategy.class);
  private final Client client;

  public GeminiStrategy(String apiKey) {
    this.client = Client.builder().apiKey(apiKey).build();
  }

  @Override
  public String chat(String prompt, AIModel model) throws AIException {
    LOGGER.info("Gemini chat – model: {}, promptSize: {}", model.getModelId(), prompt.length());
    try {
      GenerateContentResponse res = client.models.generateContent(model.getModelId(), prompt, null);
      LOGGER.info("Gemini reply");
      return res.text();
    } catch (Exception e) {
      LOGGER.error("Gemini failure", e);
      throw new AIException("Gemini failure", e);
    }
  }

  @Override
  public String vision(String prompt, URI imagePath, AIModel model) throws AIException {
    LOGGER.info("Gemini vision – model: {}, image: {}", model.getModelId(), imagePath);
    try {
      Content content = Content.fromParts(Part.fromText(prompt),
          Part.fromUri(imagePath.toString(), "image/jpeg"));
      LOGGER.info("Gemini vision reply");
      return client.models.generateContent(model.getModelId(), content, null).text();
    } catch (Exception e) {
      LOGGER.error("Gemini vision failure", e);
      throw new AIException("Gemini vision failure", e);
    }
  }
}
