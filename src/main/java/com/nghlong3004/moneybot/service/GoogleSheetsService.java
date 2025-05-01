package com.nghlong3004.moneybot.service;

import java.util.List;

public interface GoogleSheetsService {

  String readFromSheet(String spreadsheetId, String range);

  boolean writeToSheet(String spreadsheetId, String range, List<List<Object>> data);

  boolean insertRowAbove(String spreadsheetId, String sheetId, int rowIndex);

}
