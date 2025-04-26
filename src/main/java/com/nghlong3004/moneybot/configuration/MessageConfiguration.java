package com.nghlong3004.moneybot.configuration;

public class MessageConfiguration {

  private long senderId;
  private long chatID;
  private String fullname;
  private String messageText;

  public MessageConfiguration(long senderId, long chatID, String fullname, String messageText) {
    this.senderId = senderId;
    this.chatID = chatID;
    this.setFullname(fullname);
    this.messageText = messageText;
  }

  public long getSenderId() {
    return senderId;
  }

  public String getMessageText() {
    return messageText;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public long getChatID() {
    return chatID;
  }
}
