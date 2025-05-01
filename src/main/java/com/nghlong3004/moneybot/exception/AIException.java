package com.nghlong3004.moneybot.exception;

public class AIException extends RuntimeException {
  private static final long serialVersionUID = 3709324511116284943L;

  public AIException(String message, Throwable cause) {
    super(message, cause);
  }

  public AIException(String message) {
    super(message);
  }
}
