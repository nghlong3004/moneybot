package com.nghlong3004.moneybot.util;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);
  private final Properties properties = new Properties();;

  protected PropertyUtil() {
    try (InputStream input =
        getClass().getResourceAsStream("/config/config.properties")) {
      if (input == null) {
        LOGGER.error("config.properties file not found in classpath.");
        return;
      }
      properties.load(input);
      LOGGER.info("Loaded config.properties successfully.");
    } catch (Exception e) {
      LOGGER.error("Error loading config.properties: {}", e);
    }
  }

  public Properties getProperties() {
    return properties;
  }

  // --- OpenAI ---
  public String getOpenAIApiKey() {
    return getPropertyValue("openai.api_key");
  }
  
  // -- GeminiAI --
  public String getGeminiApiKey() {
    return getPropertyValue("gemini.api_key");
  }

  // --- GitHub ---
  public String getGithubApiKey() {
    return getPropertyValue("github.api_key");
  }

  // --- Binance ---
  public String getBinanceApiKey() {
    return getPropertyValue("binance.api_key");
  }

  // --- Telegram ---
  public String getTelegramToken() {
    return getPropertyValue("tele.token");
  }

  public String getTelegramUsername() {
    return getPropertyValue("tele.username");
  }

  // --- Database ---
  public String getDbUrl() {
    return getPropertyValue("db.url");
  }

  public String getDbUsername() {
    return getPropertyValue("db.username");
  }

  public String getDbPassword() {
    return getPropertyValue("db.password");
  }

  public String getDbClassName() {
    return getPropertyValue("db.classname");
  }

  private String getPropertyValue(String key) {
    String value = properties.getProperty(key);
    if (value == null) {
      LOGGER.warn("Missing property: {} ",key);
    } else {
      LOGGER.info("Loaded property: {}", key);
    }
    return value;
  }
}
