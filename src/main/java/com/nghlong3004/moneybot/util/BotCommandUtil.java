package com.nghlong3004.moneybot.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotCommandUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(BotCommandUtil.class);

  private static final String HELP_MESSAGE_VI_PATH = "/telegram/help_message_vi.txt";
  private static final String START_MESSAGE_VI_PATH = "/telegram/start_message_vi.txt";
  private static final String SHEET_GUIDE_VI_PATH = "/telegram/sheet_guide_vi.txt";
  private static final String HELP_MESSAGE_NOT_LINK_VI_PATH =
      "/telegram/help_message_not_link_vi.txt";
  private static final Pattern GOOGLE_SHEET_LINK_PATTERN =
      Pattern.compile("https://docs\\.google\\.com/spreadsheets/d/([a-zA-Z0-9-_]+)");

  private BotCommandUtil() {}

  public static String getHelpMessage() {
    String path = HELP_MESSAGE_VI_PATH;

    return getMessageForFile(path);
  }

  public static String getHelpNotLinkMessage() {
    String path = HELP_MESSAGE_NOT_LINK_VI_PATH;

    return getMessageForFile(path);
  }

  public static String getStartMessage() {
    String path = START_MESSAGE_VI_PATH;

    return getMessageForFile(path);
  }

  public static String getSheetGuideMessage() {
    String path = SHEET_GUIDE_VI_PATH;

    return getMessageForFile(path);
  }

  public static Optional<String> extractSpreadsheetId(String messageText) {
    Matcher matcher = GOOGLE_SHEET_LINK_PATTERN.matcher(messageText);
    if (matcher.find()) {
      return Optional.of(matcher.group(1));
    }
    return Optional.empty();
  }

  private static String getMessageForFile(String path) {
    try (InputStream inputStream = BotCommandUtil.class.getResourceAsStream(path);
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
      scanner.useDelimiter("\\A");
      String message = scanner.hasNext() ? scanner.next() : "Unable to load file message.";
      LOGGER.info("Successfully loaded {} file", path);
      return message;

    } catch (Exception e) {
      LOGGER.error("Error loading {} message file: ", path, e);
      return "Unable to load file message.";
    }
  }


}
