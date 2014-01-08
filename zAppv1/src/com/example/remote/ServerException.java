package com.example.remote;

public class ServerException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1707924078354581055L;

  private String mCode;

  private String mMessage;
  
  public ServerException(String code, String message){
    mCode = code;
    mMessage = message;
  }

  public String getCode() {
    return mCode;
  }

  public String getMessage() {
    return mMessage;
  }
  
}
