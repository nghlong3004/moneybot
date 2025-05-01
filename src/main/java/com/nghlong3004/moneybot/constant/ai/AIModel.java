package com.nghlong3004.moneybot.constant.ai;

public enum AIModel {

  AZURE_GPT4("openai/gpt-4.1", ProviderAI.AZURE), AZURE_GPT4_MINI("openai/gpt-4.1-mini",
      ProviderAI.AZURE), AZURE_COHERE_NANO("Cohere-command-r-plus-08-2024",
          ProviderAI.AZURE), AZURE_LLAMA_MAVERICK("meta/Llama-4-Maverick-17B-128E-Instruct-FP8",
              ProviderAI.AZURE), AZURE_DEEPSEEK_V3("deepseek/DeepSeek-V3-0324",
                  ProviderAI.AZURE), GEMINI_FLASH("gemini-2.0-flash-001", ProviderAI.GEMINI);

  private final String modelId;
  private final ProviderAI provider;

  AIModel(String modelId, ProviderAI provider) {
    this.modelId = modelId;
    this.provider = provider;
  }

  public String getModelId() {
    return modelId;
  }

  public ProviderAI getProvider() {
    return provider;
  }
}

