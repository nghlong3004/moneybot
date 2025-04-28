package com.nghlong3004.moneybot.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.nghlong3004.moneybot.constant.GoogleConstant;

public class GoogleSheetUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetUtil.class);

  private Sheets sheetsService = null;
  private Credential credential = null;

  protected GoogleSheetUtil() {
    LOGGER.info("Initialized GoogleSheetUtil");
  }

  private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
    if (this.credential == null) {
      LOGGER.info("Loading Google Credentials...");
      InputStream in =
          GoogleSheetUtil.class.getResourceAsStream(GoogleConstant.CREDENTIALS_FILE_PATH);
      if (in == null) {
        throw new FileNotFoundException(
            "Resource not found: " + GoogleConstant.CREDENTIALS_FILE_PATH);
      }
      GoogleClientSecrets clientSecrets =
          GoogleClientSecrets.load(GoogleConstant.JSON_FACTORY, new InputStreamReader(in));

      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
          GoogleConstant.JSON_FACTORY, clientSecrets, GoogleConstant.SCOPES)
              .setDataStoreFactory(
                  new FileDataStoreFactory(new java.io.File(GoogleConstant.TOKENS_DIRECTORY_PATH)))
              .setAccessType("offline").build();

      LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
      this.credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    return this.credential;
  }

  public Sheets getSheetsService() throws IOException {
    if (this.sheetsService == null) {
      LOGGER.info("Building Sheets service...");
      this.sheetsService = new Sheets.Builder(GoogleConstant.HTTP_TRANSPORT,
          GoogleConstant.JSON_FACTORY, getCredentials(GoogleConstant.HTTP_TRANSPORT))
              .setApplicationName(GoogleConstant.APPLICATION_NAME).build();
    }
    return this.sheetsService;
  }
}

