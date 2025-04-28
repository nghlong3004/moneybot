package com.nghlong3004.moneybot.constant;

import java.util.Collections;
import java.util.List;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

public class GoogleConstant {

  public static final String APPLICATION_NAME = "MoneyBot";

  public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  public static final String TOKENS_DIRECTORY_PATH = "tokens";

  public static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

  public static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  
  public static final String OAUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";

  public static NetHttpTransport HTTP_TRANSPORT;

  static {
    try {
      HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    } catch (Throwable t) {
      t.printStackTrace();
      System.exit(1);
    }
  }

}
