package com.nghlong3004.moneybot.service.ai.strategy;

import java.net.URI;
import com.nghlong3004.moneybot.constant.ai.AIModel;
import com.nghlong3004.moneybot.exception.AIException;

public interface AIClientStrategy {
  String chat(String prompt, AIModel model) throws AIException;

  default String vision(String prompt, URI imagePath, AIModel model) throws AIException {
    throw new UnsupportedOperationException("Vision not supported by this provider");
  }
}
