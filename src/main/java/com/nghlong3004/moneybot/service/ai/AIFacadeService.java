package com.nghlong3004.moneybot.service.ai;

import java.net.URI;
import java.util.EnumMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nghlong3004.moneybot.constant.ai.AIModel;
import com.nghlong3004.moneybot.constant.ai.ProviderAI;
import com.nghlong3004.moneybot.service.ai.strategy.AIClientStrategy;
import com.nghlong3004.moneybot.service.ai.strategy.AzureStrategy;
import com.nghlong3004.moneybot.service.ai.strategy.GeminiStrategy;

public final class AIFacadeService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AIFacadeService.class);

  private final Map<ProviderAI, AIClientStrategy> registry = new EnumMap<>(ProviderAI.class);
  private static AIFacadeService instance;

  public static AIFacadeService getInstance(String AzureToken, String geminiKey, String endpoint) {
    if (instance == null) {
      instance = new AIFacadeService(AzureToken, geminiKey, endpoint);
    }
    return instance;
  }

  private AIFacadeService(String AzureToken, String geminiKey, String endpoint) {
    register(ProviderAI.OPEN_AI, new AzureStrategy(AzureToken, endpoint));
    register(ProviderAI.GEMINI, new GeminiStrategy(geminiKey));
    LOGGER.info("AIServiceFacade initialised with {} providers", registry.size());
  }

  public void register(ProviderAI provider, AIClientStrategy strategy) {
    registry.put(provider, strategy);
    LOGGER.info("Registered new AI provider strategy: {}", provider);
  }

  public String ask(AIModel model, String prompt) {
    AIClientStrategy aiClientStrategy = get(model.getProvider());
    return aiClientStrategy.chat(prompt, model);
  }

  public String askVision(AIModel model, String prompt, URI image) {
    AIClientStrategy aiClientStrategy = get(model.getProvider());
    return aiClientStrategy.vision(prompt, image, model);
  }

  private AIClientStrategy get(ProviderAI p) {
    AIClientStrategy aiClientStrategy = registry.get(p);
    if (aiClientStrategy == null)
      throw new IllegalStateException("No strategy for provider " + p);
    return aiClientStrategy;
  }
}
