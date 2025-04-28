package com.nghlong3004.moneybot.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nghlong3004.moneybot.constant.GoogleConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class GoogleCredentialReaderUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCredentialReaderUtil.class);
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static JsonNode credentialData;

  static {
    try {
      InputStream in = GoogleCredentialReaderUtil.class
          .getResourceAsStream(GoogleConstant.CREDENTIALS_FILE_PATH);
      credentialData = OBJECT_MAPPER.readTree(in).get("installed");
      LOGGER.info("Loaded Google OAuth credentials from credentials.json");
    } catch (Exception e) {
      LOGGER.error("Failed to load credentials.json", e);
      throw new RuntimeException(e);
    }
  }

  public static String getClientId() {
    return credentialData.get("client_id").asText();
  }

  public static String getClientSecret() {
    return credentialData.get("client_secret").asText();
  }

  public static String getRedirectUri() {
    return credentialData.get("redirect_uris").get(0).asText();
  }

  public static String getTemplateFileId() {
    return credentialData.get("template_file_id").asText();
  }
}
