package com.nghlong3004.moneybot.service.impl;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.nghlong3004.moneybot.service.IGoogleSheetsService;
import com.nghlong3004.moneybot.util.GoogleCredentialReaderUtil;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;

public class GoogleSheetsService implements IGoogleSheetsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsService.class);

  private static final GoogleSheetsService INSTANCE = new GoogleSheetsService();

  private GoogleSheetsService() {
    LOGGER.info("Initialized GoogleSheetsService");
  }

  public static GoogleSheetsService getInstance() {
    return INSTANCE;
  }

  @Override
  public String readFromSheet(String spreadsheetId, String range) {
    LOGGER.info("Reading data from Google Sheets: spreadsheetId={}, range={}", spreadsheetId,
        range);
    try {
      ValueRange response =
          ObjectContainerUtil.getGoogleSheetUtil().getSheetsService().spreadsheets().values()
              .get(GoogleCredentialReaderUtil.getTemplateFileId(), range).execute();

      List<List<Object>> values = response.getValues();
      if (values == null || values.isEmpty()) {
        LOGGER.warn("No data found in spreadsheetId={}, range={}", spreadsheetId, range);
        return "";
      } else {
        return parseObjectToString(values);
      }
    } catch (IOException e) {
      LOGGER.error("Error reading data from spreadsheetId={}, range={}", spreadsheetId, range, e);
      return "";
    }
  }

  @Override
  public boolean writeToSheet(String spreadsheetId, String range, List<List<Object>> data) {
    LOGGER.info("Write data to Google Sheets: spreadsheetId={}, range={}", spreadsheetId, range);
    if (data == null || data.isEmpty()) {
      LOGGER.warn("No data to write for spreadsheetId={}, range={}", spreadsheetId, range);
      return false;
    }
    try {
      ValueRange body = new ValueRange().setValues(data);
      UpdateValuesResponse result =
          ObjectContainerUtil.getGoogleSheetUtil().getSheetsService().spreadsheets().values()
              .update(GoogleCredentialReaderUtil.getTemplateFileId(), range, body)
              .setValueInputOption("USER_ENTERED").execute();

      LOGGER.info("{} cells updated successfully in spreadsheetId={}", result.getUpdatedCells(),
          spreadsheetId);
      return true;
    } catch (IOException e) {
      LOGGER.error("Error writing data to spreadsheetId={}, range={}", spreadsheetId, range, e);
      return false;
    }
  }

  private String parseObjectToString(List<List<Object>> values) {
    String s = "";
    for (List<Object> value : values) {
      for (Object v : value) {
        s += v.toString() + " ";
      }
      s += '\n';
    }
    return s;
  }

}
