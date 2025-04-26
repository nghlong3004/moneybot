package com.nghlong3004.moneybot.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotCommandUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(BotCommandUtil.class);

  private static final String HELP_MESSAGE_VI_PATH = "/help_message_vi.txt";

  private BotCommandUtil() {}

  public static String getHelpMessage() {
    String path = HELP_MESSAGE_VI_PATH;

    try (InputStream inputStream = BotCommandUtil.class.getResourceAsStream(path);
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
      scanner.useDelimiter("\\A");
      String message = scanner.hasNext() ? scanner.next() : "Unable to load help message.";
      LOGGER.info("Successfully loaded help message");
      return message;

    } catch (Exception e) {
      LOGGER.error("Error loading help message file: ", e);
      return "Unable to load help message.";
    }
  }
}
