package com.nghlong3004.moneybot.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.InsertDimensionRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.nghlong3004.moneybot.service.GoogleSheetsService;
import com.nghlong3004.moneybot.util.ObjectContainerUtil;

public class GoogleSheetsServiceImpl implements GoogleSheetsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsServiceImpl.class);

  private static final GoogleSheetsServiceImpl INSTANCE = new GoogleSheetsServiceImpl();

  private GoogleSheetsServiceImpl() {
    LOGGER.info("Initialized GoogleSheetsService");
  }

  public static GoogleSheetsServiceImpl getInstance() {
    return INSTANCE;
  }

  @Override
  public String readFromSheet(String spreadsheetId, String range) {
    LOGGER.info("Reading data from Google Sheets");
    try {
      ValueRange response = ObjectContainerUtil.getGoogleSheetUtil().getSheetsService()
          .spreadsheets().values().get(spreadsheetId, range).execute();

      List<List<Object>> values = response.getValues();
      if (values == null || values.isEmpty()) {
        LOGGER.warn("No data found");
        return "";
      }
      return parseObjectToString(values);
    } catch (IOException e) {
      LOGGER.error("Error reading data from spreadsheetId={}", spreadsheetId, e);
      return "";
    }
  }

  @Override
  public boolean writeToSheet(String spreadsheetId, String range, List<List<Object>> data) {
    LOGGER.info("Write data to Google Sheets");
    if (data == null || data.isEmpty()) {
      LOGGER.warn("No data to write ");
      return false;
    }
    try {
      ValueRange body = new ValueRange().setValues(data);
      UpdateValuesResponse result =
          ObjectContainerUtil.getGoogleSheetUtil().getSheetsService().spreadsheets().values()
              .update(spreadsheetId, range, body).setValueInputOption("USER_ENTERED").execute();
      LOGGER.info("{} cells updated successfully", result.getUpdatedCells());
      return true;
    } catch (IOException e) {
      LOGGER.error("Error writing data to spreadsheetId={}, range={}", spreadsheetId, range, e);
      return false;
    }
  }

  @Override
  public boolean insertRowAbove(String spreadsheetId, String sheetName, int rowIndex) {
    LOGGER.info("Inserting new row above rowIndex={} in sheetName={}", rowIndex, sheetName);
    try {
      Integer sheetId = getSheetIdByName(spreadsheetId, sheetName);
      if (sheetId == null) {
        LOGGER.error("Sheet name '{}' not found", sheetName);
        return false;
      }
      DimensionRange dimRange = new DimensionRange().setSheetId(sheetId).setDimension("ROWS")
          .setStartIndex(rowIndex).setEndIndex(rowIndex + 1);
      InsertDimensionRequest insertReq =
          new InsertDimensionRequest().setRange(dimRange).setInheritFromBefore(rowIndex > 0);
      Request request = new Request().setInsertDimension(insertReq);
      BatchUpdateSpreadsheetRequest batchReq =
          new BatchUpdateSpreadsheetRequest().setRequests(Collections.singletonList(request));
      ObjectContainerUtil.getGoogleSheetUtil().getSheetsService().spreadsheets()
          .batchUpdate(spreadsheetId, batchReq).execute();
      LOGGER.info("Inserted row successfully.");
      return true;
    } catch (IOException e) {
      LOGGER.error("Error inserting row", e);
      return false;
    }
  }

  private Integer getSheetIdByName(String spreadsheetId, String sheetName) throws IOException {
    Spreadsheet spreadsheet = ObjectContainerUtil.getGoogleSheetUtil().getSheetsService()
        .spreadsheets().get(spreadsheetId).setFields("sheets.properties").execute();
    for (Sheet sheet : spreadsheet.getSheets()) {
      if (sheet.getProperties().getTitle().equalsIgnoreCase(sheetName)) {
        return sheet.getProperties().getSheetId();
      }
    }
    return null;
  }

  private String parseObjectToString(List<List<Object>> values) {
    String s = values.stream().map(value -> value.stream().map(Object::toString).map(String::trim)
        .collect(Collectors.joining(" "))).collect(Collectors.joining("\n"));
    return s;
  }

}
